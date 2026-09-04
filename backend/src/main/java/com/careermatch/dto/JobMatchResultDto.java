package com.careermatch.dto;

import java.util.List;

public class JobMatchResultDto {
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private Integer overallMatchScore; // 0 - 100%

    // Weight breakdowns
    private Double skillsScore;     // 50% max
    private Double experienceScore; // 15% max
    private Double educationScore;  // 10% max
    private Double locationScore;   // 10% max
    private Double salaryScore;     // 10% max
    private Double workModeScore;   // 5% max

    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> whyThisJobExplanations;
    private String matchVerdict; // e.g. "Excellent Match", "Strong Match", "Good Match", "Moderate Match"

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOverallMatchScore() {
        return overallMatchScore;
    }

    public void setOverallMatchScore(Integer overallMatchScore) {
        this.overallMatchScore = overallMatchScore;
    }

    public Double getSkillsScore() {
        return skillsScore;
    }

    public void setSkillsScore(Double skillsScore) {
        this.skillsScore = skillsScore;
    }

    public Double getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(Double experienceScore) {
        this.experienceScore = experienceScore;
    }

    public Double getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(Double educationScore) {
        this.educationScore = educationScore;
    }

    public Double getLocationScore() {
        return locationScore;
    }

    public void setLocationScore(Double locationScore) {
        this.locationScore = locationScore;
    }

    public Double getSalaryScore() {
        return salaryScore;
    }

    public void setSalaryScore(Double salaryScore) {
        this.salaryScore = salaryScore;
    }

    public Double getWorkModeScore() {
        return workModeScore;
    }

    public void setWorkModeScore(Double workModeScore) {
        this.workModeScore = workModeScore;
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

    public List<String> getWhyThisJobExplanations() {
        return whyThisJobExplanations;
    }

    public void setWhyThisJobExplanations(List<String> whyThisJobExplanations) {
        this.whyThisJobExplanations = whyThisJobExplanations;
    }

    public String getMatchVerdict() {
        return matchVerdict;
    }

    public void setMatchVerdict(String matchVerdict) {
        this.matchVerdict = matchVerdict;
    }
}
