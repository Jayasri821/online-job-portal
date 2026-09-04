package com.jobportal.dto;

public record UserDto(
        Long id,
        String fullName,
        String email,
        String role,
        boolean enabled,
        String createdAt
) {
}
