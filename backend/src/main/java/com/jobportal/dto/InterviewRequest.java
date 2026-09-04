package com.jobportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record InterviewRequest(
        @NotNull LocalDateTime scheduledAt,
        @NotBlank String mode,
        String meetingLink,
        String notes
) {
}
