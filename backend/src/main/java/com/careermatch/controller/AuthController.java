package com.careermatch.controller;

import com.careermatch.dto.ApiResponse;
import com.careermatch.dto.AuthResponse;
import com.careermatch.dto.LoginRequest;
import com.careermatch.dto.RegisterRequest;
import com.careermatch.dto.UserDto;
import com.careermatch.entity.User;
import com.careermatch.service.AuthService;
import com.careermatch.service.CurrentUserService;
import com.careermatch.service.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final CurrentUserService currentUserService;
    private final DtoMapper dtoMapper;

    public AuthController(AuthService authService, CurrentUserService currentUserService, DtoMapper dtoMapper) {
        this.authService = authService;
        this.currentUserService = currentUserService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.ok("Registration successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser() {
        User user = currentUserService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.ok("Current user profile", dtoMapper.toUserDto(user)));
    }
}
