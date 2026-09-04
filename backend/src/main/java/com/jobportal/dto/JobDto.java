package com.jobportal.dto;

import com.jobportal.entity.JobStatus;
import com.jobportal.entity.JobType;

public record JobDto(
        Long id,
        String title,
        String description,
        String location,
        String skills,
        Integer experienceYears,
        Integer salaryMin,
        Integer salaryMax,
        JobType jobType,
        JobStatus status,
        String postedAt,
        Long companyId,
        String companyName
) {
}
