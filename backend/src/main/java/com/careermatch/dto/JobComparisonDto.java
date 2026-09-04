package com.careermatch.dto;

import java.util.List;

public class JobComparisonDto {
    private List<JobDto> jobs;
    private Long candidateId;
    private String bestMatchJobTitle;
    private Long bestMatchJobId;
    private String highestSalaryJobTitle;
    private Long highestSalaryJobId;

    public List<JobDto> getJobs() {
        return jobs;
    }

    public void setJobs(List<JobDto> jobs) {
        this.jobs = jobs;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public String getBestMatchJobTitle() {
        return bestMatchJobTitle;
    }

    public void setBestMatchJobTitle(String bestMatchJobTitle) {
        this.bestMatchJobTitle = bestMatchJobTitle;
    }

    public Long getBestMatchJobId() {
        return bestMatchJobId;
    }

    public void setBestMatchJobId(Long bestMatchJobId) {
        this.bestMatchJobId = bestMatchJobId;
    }

    public String getHighestSalaryJobTitle() {
        return highestSalaryJobTitle;
    }

    public void setHighestSalaryJobTitle(String highestSalaryJobTitle) {
        this.highestSalaryJobTitle = highestSalaryJobTitle;
    }

    public Long getHighestSalaryJobId() {
        return highestSalaryJobId;
    }

    public void setHighestSalaryJobId(Long highestSalaryJobId) {
        this.highestSalaryJobId = highestSalaryJobId;
    }
}
