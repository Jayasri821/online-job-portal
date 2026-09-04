package com.careermatch.dto;

import java.util.Map;

public class DashboardStatsDto {
    // Job Seeker Stats
    private Integer careerReadinessScore;
    private Long recommendedJobsCount;
    private Long totalApplicationsCount;
    private Long savedJobsCount;
    private Long interviewsCount;
    private Long shortlistedCount;
    private Long selectedCount;
    private Long rejectedCount;
    private String topSkillToImprove;

    // Employer Stats
    private Long totalJobsPosted;
    private Long activeJobsCount;
    private Long totalApplicantsReceived;
    private Long shortlistedCandidates;
    private Long employerInterviewsCount;
    private Long selectedCandidates;

    // Admin Stats
    private Long totalUsersCount;
    private Long totalJobSeekersCount;
    private Long totalEmployersCount;
    private Long totalJobsInSystem;
    private Long openJobsCount;
    private Long pendingJobsCount;
    private Long totalApplicationsSystemWide;
    private Map<String, Long> applicationsByStatus;

    public Integer getCareerReadinessScore() {
        return careerReadinessScore;
    }

    public void setCareerReadinessScore(Integer careerReadinessScore) {
        this.careerReadinessScore = careerReadinessScore;
    }

    public Long getRecommendedJobsCount() {
        return recommendedJobsCount;
    }

    public void setRecommendedJobsCount(Long recommendedJobsCount) {
        this.recommendedJobsCount = recommendedJobsCount;
    }

    public Long getTotalApplicationsCount() {
        return totalApplicationsCount;
    }

    public void setTotalApplicationsCount(Long totalApplicationsCount) {
        this.totalApplicationsCount = totalApplicationsCount;
    }

    public Long getSavedJobsCount() {
        return savedJobsCount;
    }

    public void setSavedJobsCount(Long savedJobsCount) {
        this.savedJobsCount = savedJobsCount;
    }

    public Long getInterviewsCount() {
        return interviewsCount;
    }

    public void setInterviewsCount(Long interviewsCount) {
        this.interviewsCount = interviewsCount;
    }

    public Long getShortlistedCount() {
        return shortlistedCount;
    }

    public void setShortlistedCount(Long shortlistedCount) {
        this.shortlistedCount = shortlistedCount;
    }

    public Long getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(Long selectedCount) {
        this.selectedCount = selectedCount;
    }

    public Long getRejectedCount() {
        return rejectedCount;
    }

    public void setRejectedCount(Long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }

    public String getTopSkillToImprove() {
        return topSkillToImprove;
    }

    public void setTopSkillToImprove(String topSkillToImprove) {
        this.topSkillToImprove = topSkillToImprove;
    }

    public Long getTotalJobsPosted() {
        return totalJobsPosted;
    }

    public void setTotalJobsPosted(Long totalJobsPosted) {
        this.totalJobsPosted = totalJobsPosted;
    }

    public Long getActiveJobsCount() {
        return activeJobsCount;
    }

    public void setActiveJobsCount(Long activeJobsCount) {
        this.activeJobsCount = activeJobsCount;
    }

    public Long getTotalApplicantsReceived() {
        return totalApplicantsReceived;
    }

    public void setTotalApplicantsReceived(Long totalApplicantsReceived) {
        this.totalApplicantsReceived = totalApplicantsReceived;
    }

    public Long getShortlistedCandidates() {
        return shortlistedCandidates;
    }

    public void setShortlistedCandidates(Long shortlistedCandidates) {
        this.shortlistedCandidates = shortlistedCandidates;
    }

    public Long getEmployerInterviewsCount() {
        return employerInterviewsCount;
    }

    public void setEmployerInterviewsCount(Long employerInterviewsCount) {
        this.employerInterviewsCount = employerInterviewsCount;
    }

    public Long getSelectedCandidates() {
        return selectedCandidates;
    }

    public void setSelectedCandidates(Long selectedCandidates) {
        this.selectedCandidates = selectedCandidates;
    }

    public Long getTotalUsersCount() {
        return totalUsersCount;
    }

    public void setTotalUsersCount(Long totalUsersCount) {
        this.totalUsersCount = totalUsersCount;
    }

    public Long getTotalJobSeekersCount() {
        return totalJobSeekersCount;
    }

    public void setTotalJobSeekersCount(Long totalJobSeekersCount) {
        this.totalJobSeekersCount = totalJobSeekersCount;
    }

    public Long getTotalEmployersCount() {
        return totalEmployersCount;
    }

    public void setTotalEmployersCount(Long totalEmployersCount) {
        this.totalEmployersCount = totalEmployersCount;
    }

    public Long getTotalJobsInSystem() {
        return totalJobsInSystem;
    }

    public void setTotalJobsInSystem(Long totalJobsInSystem) {
        this.totalJobsInSystem = totalJobsInSystem;
    }

    public Long getOpenJobsCount() {
        return openJobsCount;
    }

    public void setOpenJobsCount(Long openJobsCount) {
        this.openJobsCount = openJobsCount;
    }

    public Long getPendingJobsCount() {
        return pendingJobsCount;
    }

    public void setPendingJobsCount(Long pendingJobsCount) {
        this.pendingJobsCount = pendingJobsCount;
    }

    public Long getTotalApplicationsSystemWide() {
        return totalApplicationsSystemWide;
    }

    public void setTotalApplicationsSystemWide(Long totalApplicationsSystemWide) {
        this.totalApplicationsSystemWide = totalApplicationsSystemWide;
    }

    public Map<String, Long> getApplicationsByStatus() {
        return applicationsByStatus;
    }

    public void setApplicationsByStatus(Map<String, Long> applicationsByStatus) {
        this.applicationsByStatus = applicationsByStatus;
    }
}
