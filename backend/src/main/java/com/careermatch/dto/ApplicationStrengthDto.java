package com.careermatch.dto;

import java.util.List;

public class ApplicationStrengthDto {
    private Long jobId;
    private String jobTitle;
    private Integer strengthScore; // 0 - 100
    private String verdict;
    private List<String> strongPoints;
    private List<String> weakPoints;
    private List<String> improvementSuggestions;
    private String disclaimer = "This strength score is an algorithmic career evaluation based on profile match metrics and does not guarantee employer hiring decisions.";

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

    public Integer getStrengthScore() {
        return strengthScore;
    }

    public void setStrengthScore(Integer strengthScore) {
        this.strengthScore = strengthScore;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public List<String> getStrongPoints() {
        return strongPoints;
    }

    public void setStrongPoints(List<String> strongPoints) {
        this.strongPoints = strongPoints;
    }

    public List<String> getWeakPoints() {
        return weakPoints;
    }

    public void setWeakPoints(List<String> weakPoints) {
        this.weakPoints = weakPoints;
    }

    public List<String> getImprovementSuggestions() {
        return improvementSuggestions;
    }

    public void setImprovementSuggestions(List<String> improvementSuggestions) {
        this.improvementSuggestions = improvementSuggestions;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }
}
