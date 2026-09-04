package com.careermatch.controller;

import com.careermatch.dto.ApiResponse;
import com.careermatch.dto.ApplicationDto;
import com.careermatch.dto.ApplyRequest;
import com.careermatch.dto.StatusUpdateRequest;
import com.careermatch.entity.User;
import com.careermatch.service.ApplicationService;
import com.careermatch.service.CurrentUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CurrentUserService currentUserService;

    public ApplicationController(ApplicationService applicationService, CurrentUserService currentUserService) {
        this.applicationService = applicationService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationDto>> applyForJob(@Valid @RequestBody ApplyRequest request) {
        User user = currentUserService.getCurrentUser();
        ApplicationDto application = applicationService.applyForJob(user, request);
        return ResponseEntity.ok(ApiResponse.ok("Application submitted successfully", application));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getMyApplications() {
        User user = currentUserService.getCurrentUser();
        List<ApplicationDto> applications = applicationService.getCandidateApplications(user);
        return ResponseEntity.ok(ApiResponse.ok("Candidate applications", applications));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApplicationDto>> getApplicationById(@PathVariable Long id) {
        User user = currentUserService.getCurrentUser();
        ApplicationDto application = applicationService.getApplicationById(id, user);
        return ResponseEntity.ok(ApiResponse.ok("Application details", application));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ApplicationDto>> updateApplicationStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusUpdateRequest request
    ) {
        User user = currentUserService.getCurrentUser();
        ApplicationDto updated = applicationService.updateApplicationStatus(id, request, user);
        return ResponseEntity.ok(ApiResponse.ok("Application status updated", updated));
    }
}
