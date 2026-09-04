package com.jobportal.dto;

import com.jobportal.entity.JobType;
import jakarta.validation.constraints.NotBlank;

public record JobRequest(
        @NotBlank String title,
        @NotBlank String description,
        String location,
        String skills,
        Integer experienceYears,
        Integer salaryMin,
        Integer salaryMax,
        JobType jobType
) {
}
