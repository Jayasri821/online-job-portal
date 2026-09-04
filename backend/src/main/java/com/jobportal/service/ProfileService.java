package com.jobportal.service;

import com.jobportal.dto.ProfileDto;
import com.jobportal.dto.ProfileUpdateRequest;
import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.User;
import com.jobportal.exception.ApiException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.JobSeekerProfileRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

    private final JobSeekerProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final CurrentUserService currentUserService;

    public ProfileService(
            JobSeekerProfileRepository profileRepository,
            UserRepository userRepository,
            FileStorageService fileStorageService,
            CurrentUserService currentUserService
    ) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.currentUserService = currentUserService;
    }

    public ProfileDto getMyProfile() {
        User user = currentUserService.requireUser();
        JobSeekerProfile profile = getOrCreate(user);
        return DtoMapper.toProfile(user, profile);
    }

    @Transactional
    public ProfileDto updateMyProfile(ProfileUpdateRequest request) {
        User user = currentUserService.requireUser();
        if (request.fullName() != null && !request.fullName().isBlank()) {
            user.setFullName(request.fullName());
            userRepository.save(user);
        }
        JobSeekerProfile profile = getOrCreate(user);
        profile.setPhone(request.phone());
        profile.setLocation(request.location());
        profile.setEducation(request.education());
        profile.setExperience(request.experience());
        profile.setSkills(request.skills());
        profile.setSummary(request.summary());
        profileRepository.save(profile);
        return DtoMapper.toProfile(user, profile);
    }

    @Transactional
    public ProfileDto uploadResume(MultipartFile file) {
        User user = currentUserService.requireUser();
        JobSeekerProfile profile = getOrCreate(user);
        profile.setResumeFileName(fileStorageService.saveResume(file));
        profileRepository.save(profile);
        return DtoMapper.toProfile(user, profile);
    }

    public JobSeekerProfile getProfileFor(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Seeker profile not found"));
    }

    public void requireResume(User user) {
        JobSeekerProfile profile = getOrCreate(user);
        if (profile.getResumeFileName() == null || profile.getResumeFileName().isBlank()) {
            throw new ApiException("Please upload your resume before applying", 400);
        }
    }

    private JobSeekerProfile getOrCreate(User user) {
        return profileRepository.findByUser(user).orElseGet(() -> {
            JobSeekerProfile profile = new JobSeekerProfile();
            profile.setUser(user);
            return profileRepository.save(profile);
        });
    }
}
