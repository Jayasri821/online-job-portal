package com.jobportal.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
        @NotBlank String name,
        String website,
        String location,
        String industry,
        String description
) {
}
