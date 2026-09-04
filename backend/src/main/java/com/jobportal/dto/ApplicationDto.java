package com.jobportal.dto;

import com.jobportal.entity.ApplicationStatus;

public record ApplicationDto(
        Long id,
        Long jobId,
        String jobTitle,
        String companyName,
        Long seekerId,
        String seekerName,
        String seekerEmail,
        String coverLetter,
        ApplicationStatus status,
        String appliedAt,
        String interviewTime,
        String interviewMode,
        String meetingLink
) {
}
