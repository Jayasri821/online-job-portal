package com.jobportal.service;

import com.jobportal.dto.*;
import com.jobportal.entity.*;

import java.time.format.DateTimeFormatter;

public final class DtoMapper {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DtoMapper() {
    }

    public static UserDto toUser(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled(),
                user.getCreatedAt() == null ? null : user.getCreatedAt().format(FMT)
        );
    }

    public static ProfileDto toProfile(User user, JobSeekerProfile profile) {
        return new ProfileDto(
                profile.getId(),
                user.getFullName(),
                user.getEmail(),
                profile.getPhone(),
                profile.getLocation(),
                profile.getEducation(),
                profile.getExperience(),
                profile.getSkills(),
                profile.getSummary(),
                profile.getResumeFileName()
        );
    }

    public static CompanyDto toCompany(Company company) {
        return new CompanyDto(
                company.getId(),
                company.getName(),
                company.getWebsite(),
                company.getLocation(),
                company.getIndustry(),
                company.getDescription(),
                company.getRecruiter().getId(),
                company.getRecruiter().getFullName()
        );
    }

    public static JobDto toJob(Job job) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getSkills(),
                job.getExperienceYears(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getJobType(),
                job.getStatus(),
                job.getPostedAt() == null ? null : job.getPostedAt().format(FMT),
                job.getCompany().getId(),
                job.getCompany().getName()
        );
    }

    public static ApplicationDto toApplication(JobApplication application) {
        Interview interview = application.getInterview();
        return new ApplicationDto(
                application.getId(),
                application.getJob().getId(),
                application.getJob().getTitle(),
                application.getJob().getCompany().getName(),
                application.getSeeker().getId(),
                application.getSeeker().getFullName(),
                application.getSeeker().getEmail(),
                application.getCoverLetter(),
                application.getStatus(),
                application.getAppliedAt() == null ? null : application.getAppliedAt().format(FMT),
                interview == null || interview.getScheduledAt() == null ? null : interview.getScheduledAt().format(FMT),
                interview == null ? null : interview.getMode(),
                interview == null ? null : interview.getMeetingLink()
        );
    }
}
