package com.careermatch.dto;

import com.careermatch.entity.JobStatus;
import com.careermatch.entity.JobType;
import com.careermatch.entity.WorkMode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class JobDto {
    private Long id;
    private EmployerProfileDto employer;
    private String title;
    private String description;
    private String responsibilities;
    private String qualifications;
    private String requiredSkills;
    private List<String> requiredSkillsList;
    private String location;
    private Integer experienceYears;
    private Integer salaryMin;
    private Integer salaryMax;
    private JobType jobType;
    private WorkMode workMode;
    private LocalDate applicationDeadline;
    private JobStatus status;
    private LocalDateTime postedAt;

    // Dynamic AI Matching & Candidate Context
    private Integer matchScore;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> whyThisJob;
    private Long daysLeft;
    private Boolean isClosingSoon;
    private Boolean isSaved;
    private Boolean hasApplied;
    private String applicationStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployerProfileDto getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerProfileDto employer) {
        this.employer = employer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<String> getRequiredSkillsList() {
        return requiredSkillsList;
    }

    public void setRequiredSkillsList(List<String> requiredSkillsList) {
        this.requiredSkillsList = requiredSkillsList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Integer getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(Integer salaryMin) {
        this.salaryMin = salaryMin;
    }

    public Integer getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(Integer salaryMax) {
        this.salaryMax = salaryMax;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkMode workMode) {
        this.workMode = workMode;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public Integer getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(Integer matchScore) {
        this.matchScore = matchScore;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getWhyThisJob() {
        return whyThisJob;
    }

    public void setWhyThisJob(List<String> whyThisJob) {
        this.whyThisJob = whyThisJob;
    }

    public Long getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Long daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Boolean getIsClosingSoon() {
        return isClosingSoon;
    }

    public void setIsClosingSoon(Boolean isClosingSoon) {
        this.isClosingSoon = isClosingSoon;
    }

    public Boolean getIsSaved() {
        return isSaved;
    }

    public void setIsSaved(Boolean isSaved) {
        this.isSaved = isSaved;
    }

    public Boolean getHasApplied() {
        return hasApplied;
    }

    public void setHasApplied(Boolean hasApplied) {
        this.hasApplied = hasApplied;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
