package com.jobportal.controller;

import com.jobportal.dto.*;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.RecruiterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    private final RecruiterService recruiterService;
    private final ApplicationService applicationService;

    public RecruiterController(RecruiterService recruiterService, ApplicationService applicationService) {
        this.recruiterService = recruiterService;
        this.applicationService = applicationService;
    }

    @GetMapping("/company")
    public CompanyDto company() {
        return recruiterService.getMyCompany();
    }

    @PutMapping("/company")
    public CompanyDto saveCompany(@Valid @RequestBody CompanyRequest request) {
        return recruiterService.saveCompany(request);
    }

    @GetMapping("/jobs")
    public List<JobDto> jobs() {
        return recruiterService.getMyJobs();
    }

    @PostMapping("/jobs")
    @ResponseStatus(HttpStatus.CREATED)
    public JobDto createJob(@Valid @RequestBody JobRequest request) {
        return recruiterService.createJob(request);
    }

    @PutMapping("/jobs/{id}")
    public JobDto updateJob(@PathVariable Long id, @Valid @RequestBody JobRequest request) {
        return recruiterService.updateJob(id, request);
    }

    @PutMapping("/jobs/{id}/close")
    public JobDto closeJob(@PathVariable Long id) {
        return recruiterService.closeJob(id);
    }

    @GetMapping("/jobs/{id}/applications")
    public List<ApplicationDto> applicants(@PathVariable Long id) {
        return applicationService.applicantsForJob(id);
    }

    @PutMapping("/applications/{id}/status")
    public ApplicationDto updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return applicationService.updateStatus(id, request.status());
    }

    @PostMapping("/applications/{id}/interview")
    public ApplicationDto scheduleInterview(@PathVariable Long id, @Valid @RequestBody InterviewRequest request) {
        return applicationService.scheduleInterview(id, request);
    }
}
