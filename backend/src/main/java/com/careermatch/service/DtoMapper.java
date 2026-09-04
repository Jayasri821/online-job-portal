package com.careermatch.service;

import com.careermatch.dto.*;
import com.careermatch.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class DtoMapper {

    private final JobMatchingService jobMatchingService;

    public DtoMapper(JobMatchingService jobMatchingService) {
        this.jobMatchingService = jobMatchingService;
    }

    public UserDto toUserDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setEnabled(user.isEnabled());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public EmployerProfileDto toEmployerDto(EmployerProfile emp) {
        if (emp == null) return null;
        EmployerProfileDto dto = new EmployerProfileDto();
        dto.setId(emp.getId());
        dto.setUserId(emp.getUser().getId());
        dto.setEmail(emp.getUser().getEmail());
        dto.setFullName(emp.getUser().getFullName());
        dto.setCompanyName(emp.getCompanyName());
        dto.setIndustry(emp.getIndustry());
        dto.setLocation(emp.getLocation());
        dto.setWebsite(emp.getWebsite());
        dto.setDescription(emp.getDescription());
        dto.setLogoUrl(emp.getLogoUrl());
        dto.setVerified(emp.isVerified());
        dto.setCreatedAt(emp.getCreatedAt());
        return dto;
    }

    public JobSeekerProfileDto toSeekerDto(JobSeekerProfile profile) {
        if (profile == null) return null;
        JobSeekerProfileDto dto = new JobSeekerProfileDto();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setFullName(profile.getUser().getFullName());
        dto.setEmail(profile.getUser().getEmail());
        dto.setPhone(profile.getUser().getPhone());
        dto.setEducation(profile.getEducation());
        dto.setDegree(profile.getDegree());
        dto.setGraduationYear(profile.getGraduationYear());
        dto.setSkills(profile.getSkills());
        dto.setExperienceYears(profile.getExperienceYears());
        dto.setPreferredRole(profile.getPreferredRole());
        dto.setPreferredLocation(profile.getPreferredLocation());
        dto.setExpectedSalary(profile.getExpectedSalary());
        dto.setPreferredWorkMode(profile.getPreferredWorkMode());
        dto.setCareerObjective(profile.getCareerObjective());
        dto.setProjects(profile.getProjects());
        dto.setCertifications(profile.getCertifications());
        dto.setInternships(profile.getInternships());
        dto.setAchievements(profile.getAchievements());
        dto.setResumeUrl(profile.getResumeUrl());
        dto.setResumeOriginalName(profile.getResumeOriginalName());
        return dto;
    }

    public JobDto toJobDto(Job job, JobSeekerProfile candidateProfile, Boolean isSaved, Boolean hasApplied, String appStatus) {
        if (job == null) return null;
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setEmployer(toEmployerDto(job.getEmployer()));
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setResponsibilities(job.getResponsibilities());
        dto.setQualifications(job.getQualifications());
        dto.setRequiredSkills(job.getRequiredSkills());
        dto.setRequiredSkillsList(jobMatchingService.parseSkills(job.getRequiredSkills()));
        dto.setLocation(job.getLocation());
        dto.setExperienceYears(job.getExperienceYears());
        dto.setSalaryMin(job.getSalaryMin());
        dto.setSalaryMax(job.getSalaryMax());
        dto.setJobType(job.getJobType());
        dto.setWorkMode(job.getWorkMode());
        dto.setApplicationDeadline(job.getApplicationDeadline());
        dto.setStatus(job.getStatus());
        dto.setPostedAt(job.getPostedAt());

        // Deadline calculations
        if (job.getApplicationDeadline() != null) {
            long days = ChronoUnit.DAYS.between(LocalDate.now(), job.getApplicationDeadline());
            dto.setDaysLeft(days);
            dto.setIsClosingSoon(days >= 0 && days <= 3);
        } else {
            dto.setDaysLeft(null);
            dto.setIsClosingSoon(false);
        }

        // Candidate Matching context
        if (candidateProfile != null) {
            JobMatchResultDto matchResult = jobMatchingService.calculateMatch(candidateProfile, job);
            dto.setMatchScore(matchResult.getOverallMatchScore());
            dto.setMatchedSkills(matchResult.getMatchedSkills());
            dto.setMissingSkills(matchResult.getMissingSkills());
            dto.setWhyThisJob(matchResult.getWhyThisJobExplanations());
        }

        dto.setIsSaved(isSaved != null ? isSaved : false);
        dto.setHasApplied(hasApplied != null ? hasApplied : false);
        dto.setApplicationStatus(appStatus);

        return dto;
    }

    public ApplicationDto toApplicationDto(JobApplication app, JobSeekerProfile profile) {
        if (app == null) return null;
        ApplicationDto dto = new ApplicationDto();
        dto.setId(app.getId());
        dto.setCandidateId(app.getCandidate().getId());
        dto.setCandidateName(app.getCandidate().getFullName());
        dto.setCandidateEmail(app.getCandidate().getEmail());
        dto.setCandidatePhone(app.getCandidate().getPhone());

        if (profile != null) {
            dto.setCandidateDegree(profile.getDegree());
            dto.setCandidateGraduationYear(profile.getGraduationYear());
            dto.setCandidateExperienceYears(profile.getExperienceYears());
            dto.setCandidateSkills(profile.getSkills());
        }

        dto.setJob(toJobDto(app.getJob(), profile, false, true, app.getStatus().name()));
        dto.setStatus(app.getStatus());
        dto.setResumeUrl(app.getResumeUrl());
        dto.setCoverLetter(app.getCoverLetter());
        dto.setRecruiterNotes(app.getRecruiterNotes());
        dto.setMatchScore(app.getMatchScoreAtApplication());
        dto.setAppliedAt(app.getAppliedAt());
        dto.setUpdatedAt(app.getUpdatedAt());
        return dto;
    }

    public InterviewQuestionDto toQuestionDto(InterviewQuestion q, InterviewProgress prog) {
        if (q == null) return null;
        InterviewQuestionDto dto = new InterviewQuestionDto();
        dto.setId(q.getId());
        dto.setCategory(q.getCategory());
        dto.setTargetRole(q.getTargetRole());
        dto.setQuestionType(q.getQuestionType());
        dto.setQuestion(q.getQuestion());
        dto.setSampleAnswer(q.getSampleAnswer());
        dto.setKeyPoints(q.getKeyPoints());
        dto.setDifficultyLevel(q.getDifficultyLevel());

        if (prog != null) {
            dto.setPracticeStatus(prog.getStatus());
            dto.setUserNotes(prog.getNotes());
        }
        return dto;
    }
}
