package com.careermatch.controller;

import com.careermatch.dto.*;
import com.careermatch.entity.User;
import com.careermatch.service.CurrentUserService;
import com.careermatch.service.EmployerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    private final EmployerService employerService;
    private final CurrentUserService currentUserService;

    public EmployerController(EmployerService employerService, CurrentUserService currentUserService) {
        this.employerService = employerService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<EmployerProfileDto>> getProfile() {
        User user = currentUserService.getCurrentUser();
        EmployerProfileDto profile = employerService.getProfileDto(user);
        return ResponseEntity.ok(ApiResponse.ok("Employer profile", profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<EmployerProfileDto>> updateProfile(@RequestBody EmployerProfileDto dto) {
        User user = currentUserService.getCurrentUser();
        EmployerProfileDto updated = employerService.updateProfile(user, dto);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated", updated));
    }

    @PostMapping("/jobs")
    public ResponseEntity<ApiResponse<JobDto>> postJob(@Valid @RequestBody JobRequest request) {
        User user = currentUserService.getCurrentUser();
        JobDto job = employerService.postJob(user, request);
        return ResponseEntity.ok(ApiResponse.ok("Job posted successfully", job));
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse<JobDto>> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequest request) {
        User user = currentUserService.getCurrentUser();
        JobDto updated = employerService.updateJob(id, request, user);
        return ResponseEntity.ok(ApiResponse.ok("Job updated successfully", updated));
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse<String>> deleteJob(@PathVariable Long id) {
        User user = currentUserService.getCurrentUser();
        employerService.deleteJob(id, user);
        return ResponseEntity.ok(ApiResponse.ok("Job deleted successfully", "Deleted job #" + id));
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<List<JobDto>>> getPostedJobs() {
        User user = currentUserService.getCurrentUser();
        List<JobDto> jobs = employerService.getEmployerJobs(user);
        return ResponseEntity.ok(ApiResponse.ok("Employer posted jobs", jobs));
    }

    @GetMapping("/jobs/{id}/applicants")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getApplicantsForJob(@PathVariable Long id) {
        User user = currentUserService.getCurrentUser();
        List<ApplicationDto> applicants = employerService.getApplicantsForJob(id, user);
        return ResponseEntity.ok(ApiResponse.ok("Ranked candidates for job #" + id, applicants));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        User user = currentUserService.getCurrentUser();
        DashboardStatsDto stats = employerService.getEmployerDashboardStats(user);
        return ResponseEntity.ok(ApiResponse.ok("Employer dashboard stats", stats));
    }
}
