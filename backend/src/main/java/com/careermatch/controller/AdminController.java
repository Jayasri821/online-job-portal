package com.careermatch.controller;

import com.careermatch.dto.ApiResponse;
import com.careermatch.dto.ApplicationDto;
import com.careermatch.dto.DashboardStatsDto;
import com.careermatch.dto.JobDto;
import com.careermatch.dto.UserDto;
import com.careermatch.entity.JobStatus;
import com.careermatch.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        DashboardStatsDto stats = adminService.getAdminStats();
        return ResponseEntity.ok(ApiResponse.ok("Admin system metrics", stats));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = adminService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.ok("All registered users", users));
    }

    @PutMapping("/users/{id}/toggle-status")
    public ResponseEntity<ApiResponse<String>> toggleUserStatus(@PathVariable Long id) {
        adminService.toggleUserStatus(id);
        return ResponseEntity.ok(ApiResponse.ok("User status toggled", "Status updated for user #" + id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.ok("User deleted", "Deleted user #" + id));
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<List<JobDto>>> getAllJobs() {
        List<JobDto> jobs = adminService.getAllJobs();
        return ResponseEntity.ok(ApiResponse.ok("All system jobs", jobs));
    }

    @PutMapping("/jobs/{id}/status")
    public ResponseEntity<ApiResponse<JobDto>> updateJobStatus(@PathVariable Long id, @RequestParam JobStatus status) {
        JobDto job = adminService.updateJobStatus(id, status);
        return ResponseEntity.ok(ApiResponse.ok("Job status updated", job));
    }

    @GetMapping("/applications")
    public ResponseEntity<ApiResponse<List<ApplicationDto>>> getAllApplications() {
        List<ApplicationDto> applications = adminService.getAllApplications();
        return ResponseEntity.ok(ApiResponse.ok("All system applications", applications));
    }
}
