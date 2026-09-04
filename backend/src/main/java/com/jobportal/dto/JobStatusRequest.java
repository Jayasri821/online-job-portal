package com.jobportal.dto;

import com.jobportal.entity.JobStatus;
import jakarta.validation.constraints.NotNull;

public record JobStatusRequest(
        @NotNull JobStatus status
) {
}
