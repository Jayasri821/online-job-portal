package com.careermatch.service;

import com.careermatch.dto.CareerReadinessDto;
import com.careermatch.dto.JobSeekerProfileDto;
import com.careermatch.entity.JobSeekerProfile;
import com.careermatch.entity.User;
import com.careermatch.exception.ResourceNotFoundException;
import com.careermatch.repository.JobSeekerProfileRepository;
import com.careermatch.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final JobSeekerProfileRepository seekerProfileRepository;
    private final UserRepository userRepository;
    private final CareerScoreService careerScoreService;
    private final DtoMapper dtoMapper;

    public ProfileService(JobSeekerProfileRepository seekerProfileRepository,
                          UserRepository userRepository,
                          CareerScoreService careerScoreService,
                          DtoMapper dtoMapper) {
        this.seekerProfileRepository = seekerProfileRepository;
        this.userRepository = userRepository;
        this.careerScoreService = careerScoreService;
        this.dtoMapper = dtoMapper;
    }

    public JobSeekerProfileDto getProfileForUser(User user) {
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user)
                .orElseGet(() -> {
                    JobSeekerProfile p = new JobSeekerProfile();
                    p.setUser(user);
                    return seekerProfileRepository.save(p);
                });

        JobSeekerProfileDto dto = dtoMapper.toSeekerDto(profile);
        CareerReadinessDto cr = careerScoreService.calculateCareerReadiness(profile);
        dto.setCareerReadinessScore(cr.getOverallScore());
        return dto;
    }

    @Transactional
    public JobSeekerProfileDto updateProfile(User user, JobSeekerProfileDto updateDto) {
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user)
                .orElseGet(() -> {
                    JobSeekerProfile p = new JobSeekerProfile();
                    p.setUser(user);
                    return p;
                });

        if (updateDto.getFullName() != null) user.setFullName(updateDto.getFullName());
        if (updateDto.getPhone() != null) user.setPhone(updateDto.getPhone());
        userRepository.save(user);

        if (updateDto.getEducation() != null) profile.setEducation(updateDto.getEducation());
        if (updateDto.getDegree() != null) profile.setDegree(updateDto.getDegree());
        if (updateDto.getGraduationYear() != null) profile.setGraduationYear(updateDto.getGraduationYear());
        if (updateDto.getSkills() != null) profile.setSkills(updateDto.getSkills());
        if (updateDto.getExperienceYears() != null) profile.setExperienceYears(updateDto.getExperienceYears());
        if (updateDto.getPreferredRole() != null) profile.setPreferredRole(updateDto.getPreferredRole());
        if (updateDto.getPreferredLocation() != null) profile.setPreferredLocation(updateDto.getPreferredLocation());
        if (updateDto.getExpectedSalary() != null) profile.setExpectedSalary(updateDto.getExpectedSalary());
        if (updateDto.getPreferredWorkMode() != null) profile.setPreferredWorkMode(updateDto.getPreferredWorkMode());
        if (updateDto.getCareerObjective() != null) profile.setCareerObjective(updateDto.getCareerObjective());
        if (updateDto.getProjects() != null) profile.setProjects(updateDto.getProjects());
        if (updateDto.getCertifications() != null) profile.setCertifications(updateDto.getCertifications());
        if (updateDto.getInternships() != null) profile.setInternships(updateDto.getInternships());
        if (updateDto.getAchievements() != null) profile.setAchievements(updateDto.getAchievements());
        if (updateDto.getResumeUrl() != null) profile.setResumeUrl(updateDto.getResumeUrl());
        if (updateDto.getResumeOriginalName() != null) profile.setResumeOriginalName(updateDto.getResumeOriginalName());

        profile = seekerProfileRepository.save(profile);

        JobSeekerProfileDto result = dtoMapper.toSeekerDto(profile);
        CareerReadinessDto cr = careerScoreService.calculateCareerReadiness(profile);
        result.setCareerReadinessScore(cr.getOverallScore());
        return result;
    }
}
