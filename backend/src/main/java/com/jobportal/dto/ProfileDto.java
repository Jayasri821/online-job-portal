package com.jobportal.dto;

public record ProfileDto(
        Long id,
        String fullName,
        String email,
        String phone,
        String location,
        String education,
        String experience,
        String skills,
        String summary,
        String resumeFileName
) {
}
