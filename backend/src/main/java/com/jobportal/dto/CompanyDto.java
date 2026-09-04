package com.jobportal.dto;

public record CompanyDto(
        Long id,
        String name,
        String website,
        String location,
        String industry,
        String description,
        Long recruiterId,
        String recruiterName
) {
}
