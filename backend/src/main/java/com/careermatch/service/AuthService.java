package com.careermatch.service;

import com.careermatch.dto.AuthResponse;
import com.careermatch.dto.LoginRequest;
import com.careermatch.dto.RegisterRequest;
import com.careermatch.entity.EmployerProfile;
import com.careermatch.entity.JobSeekerProfile;
import com.careermatch.entity.Role;
import com.careermatch.entity.User;
import com.careermatch.exception.ApiException;
import com.careermatch.repository.EmployerProfileRepository;
import com.careermatch.repository.JobSeekerProfileRepository;
import com.careermatch.repository.UserRepository;
import com.careermatch.security.CustomUserDetails;
import com.careermatch.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository seekerProfileRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       JobSeekerProfileRepository seekerProfileRepository,
                       EmployerProfileRepository employerProfileRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.seekerProfileRepository = seekerProfileRepository;
        this.employerProfileRepository = employerProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new ApiException("An account with this email address already exists.");
        }

        User user = new User();
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName().trim());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole() != null ? request.getRole() : Role.JOB_SEEKER);
        user.setEnabled(true);
        user = userRepository.save(user);

        Long profileId = null;
        String companyName = null;

        if (user.getRole() == Role.JOB_SEEKER) {
            JobSeekerProfile profile = new JobSeekerProfile();
            profile.setUser(user);
            profile.setEducation(request.getEducation());
            profile.setDegree(request.getDegree());
            profile.setGraduationYear(request.getGraduationYear());
            profile.setSkills(request.getSkills());
            profile.setExperienceYears(request.getExperienceYears() != null ? request.getExperienceYears() : 0);
            profile.setPreferredRole(request.getPreferredRole());
            profile.setPreferredLocation(request.getPreferredLocation());
            profile.setExpectedSalary(request.getExpectedSalary());
            profile.setPreferredWorkMode(request.getPreferredWorkMode());
            profile = seekerProfileRepository.save(profile);
            profileId = profile.getId();
        } else if (user.getRole() == Role.EMPLOYER) {
            EmployerProfile profile = new EmployerProfile();
            profile.setUser(user);
            profile.setCompanyName(request.getCompanyName() != null ? request.getCompanyName() : user.getFullName() + " Org");
            profile.setIndustry(request.getIndustry());
            profile.setLocation(request.getCompanyLocation());
            profile.setWebsite(request.getWebsite());
            profile.setDescription(request.getCompanyDescription());
            profile.setVerified(true);
            profile = employerProfileRepository.save(profile);
            profileId = profile.getId();
            companyName = profile.getCompanyName();
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);
        String token = jwtService.generateToken(userDetails, user.getId(), user.getRole().name(), user.getFullName());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFullName(), user.getRole(), profileId, companyName);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail().trim().toLowerCase(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        Long profileId = null;
        String companyName = null;

        if (user.getRole() == Role.JOB_SEEKER) {
            seekerProfileRepository.findByUser(user).ifPresent(p -> {});
            JobSeekerProfile p = seekerProfileRepository.findByUser(user).orElse(null);
            if (p != null) profileId = p.getId();
        } else if (user.getRole() == Role.EMPLOYER) {
            EmployerProfile p = employerProfileRepository.findByUser(user).orElse(null);
            if (p != null) {
                profileId = p.getId();
                companyName = p.getCompanyName();
            }
        }

        String token = jwtService.generateToken(userDetails, user.getId(), user.getRole().name(), user.getFullName());

        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFullName(), user.getRole(), profileId, companyName);
    }
}
