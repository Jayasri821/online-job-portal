package com.jobportal.dto;

public record ProfileUpdateRequest(
        String fullName,
        String phone,
        String location,
        String education,
        String experience,
        String skills,
        String summary
) {
}
