package com.jobportal.dto;

public record StatsDto(
        long totalUsers,
        long jobSeekers,
        long recruiters,
        long companies,
        long openJobs,
        long totalJobs,
        long totalApplications,
        long shortlisted,
        long interviews,
        long rejected
) {
}
