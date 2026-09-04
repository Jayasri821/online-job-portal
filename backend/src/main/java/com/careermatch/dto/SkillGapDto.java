package com.careermatch.dto;

import java.util.List;

public class SkillGapDto {
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private List<String> candidateSkills;
    private List<String> requiredSkills;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private Double skillMatchPercentage;
    private List<RecommendedSkillDto> recommendedSkillsToLearn;

    public static class RecommendedSkillDto {
        private String skillName;
        private String priority; // "HIGH", "MEDIUM", "LOW"
        private String reason;
        private String learningResource;

        public RecommendedSkillDto() {}

        public RecommendedSkillDto(String skillName, String priority, String reason, String learningResource) {
            this.skillName = skillName;
            this.priority = priority;
            this.reason = reason;
            this.learningResource = learningResource;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getLearningResource() {
            return learningResource;
        }

        public void setLearningResource(String learningResource) {
            this.learningResource = learningResource;
        }
    }

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

    public List<String> getCandidateSkills() {
        return candidateSkills;
    }

    public void setCandidateSkills(List<String> candidateSkills) {
        this.candidateSkills = candidateSkills;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
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

    public Double getSkillMatchPercentage() {
        return skillMatchPercentage;
    }

    public void setSkillMatchPercentage(Double skillMatchPercentage) {
        this.skillMatchPercentage = skillMatchPercentage;
    }

    public List<RecommendedSkillDto> getRecommendedSkillsToLearn() {
        return recommendedSkillsToLearn;
    }

    public void setRecommendedSkillsToLearn(List<RecommendedSkillDto> recommendedSkillsToLearn) {
        this.recommendedSkillsToLearn = recommendedSkillsToLearn;
    }
}
