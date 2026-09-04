package com.careermatch.dto;

import java.util.List;

public class CareerReadinessDto {
    private Integer overallScore; // 0 - 100
    private String scoreRating;   // "Industry Ready", "Nearly Ready", "Intermediate", "Developing"
    private String topSkillGap;

    // Breakdown components
    private Integer profileCompletenessScore; // max 15
    private Integer technicalSkillsScore;     // max 20
    private Integer educationScore;           // max 10
    private Integer projectsScore;            // max 15
    private Integer certificationsScore;      // max 10
    private Integer experienceScore;          // max 10
    private Integer resumeScore;              // max 10
    private Integer interviewPrepScore;       // max 10

    // Actionable improvement suggestions with point increments e.g. "+7 Add 2 GitHub Projects"
    private List<SuggestionDto> suggestions;

    public static class SuggestionDto {
        private Integer points;
        private String action;
        private String category;

        public SuggestionDto() {}

        public SuggestionDto(Integer points, String action, String category) {
            this.points = points;
            this.action = action;
            this.category = category;
        }

        public Integer getPoints() {
            return points;
        }

        public void setPoints(Integer points) {
            this.points = points;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }

    public Integer getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }

    public String getScoreRating() {
        return scoreRating;
    }

    public void setScoreRating(String scoreRating) {
        this.scoreRating = scoreRating;
    }

    public String getTopSkillGap() {
        return topSkillGap;
    }

    public void setTopSkillGap(String topSkillGap) {
        this.topSkillGap = topSkillGap;
    }

    public Integer getProfileCompletenessScore() {
        return profileCompletenessScore;
    }

    public void setProfileCompletenessScore(Integer profileCompletenessScore) {
        this.profileCompletenessScore = profileCompletenessScore;
    }

    public Integer getTechnicalSkillsScore() {
        return technicalSkillsScore;
    }

    public void setTechnicalSkillsScore(Integer technicalSkillsScore) {
        this.technicalSkillsScore = technicalSkillsScore;
    }

    public Integer getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(Integer educationScore) {
        this.educationScore = educationScore;
    }

    public Integer getProjectsScore() {
        return projectsScore;
    }

    public void setProjectsScore(Integer projectsScore) {
        this.projectsScore = projectsScore;
    }

    public Integer getCertificationsScore() {
        return certificationsScore;
    }

    public void setCertificationsScore(Integer certificationsScore) {
        this.certificationsScore = certificationsScore;
    }

    public Integer getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(Integer experienceScore) {
        this.experienceScore = experienceScore;
    }

    public Integer getResumeScore() {
        return resumeScore;
    }

    public void setResumeScore(Integer resumeScore) {
        this.resumeScore = resumeScore;
    }

    public Integer getInterviewPrepScore() {
        return interviewPrepScore;
    }

    public void setInterviewPrepScore(Integer interviewPrepScore) {
        this.interviewPrepScore = interviewPrepScore;
    }

    public List<SuggestionDto> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestionDto> suggestions) {
        this.suggestions = suggestions;
    }
}
