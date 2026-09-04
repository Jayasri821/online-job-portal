package com.jobportal.controller;

import com.jobportal.dto.*;
import com.jobportal.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/stats")
    public StatsDto stats() {
        return adminService.stats();
    }

    @GetMapping("/users")
    public List<UserDto> users() {
        return adminService.users();
    }

    @PutMapping("/users/{id}/enabled")
    public UserDto toggleUser(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Boolean enabled = body.get("enabled");
        return adminService.toggleUser(id, enabled != null && enabled);
    }

    @GetMapping("/companies")
    public List<CompanyDto> companies() {
        return adminService.companies();
    }

    @GetMapping("/jobs")
    public List<JobDto> jobs() {
        return adminService.jobs();
    }

    @PutMapping("/jobs/{id}/status")
    public JobDto jobStatus(@PathVariable Long id, @Valid @RequestBody JobStatusRequest request) {
        return adminService.updateJobStatus(id, request.status());
    }

    @GetMapping("/applications")
    public List<ApplicationDto> applications() {
        return adminService.applications();
    }
}
