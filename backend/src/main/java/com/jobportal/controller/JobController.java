package com.jobportal.controller;

import com.jobportal.dto.JobDto;
import com.jobportal.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobDto> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String jobType
    ) {
        return jobService.search(keyword, location, jobType);
    }

    @GetMapping("/{id}")
    public JobDto get(@PathVariable Long id) {
        return jobService.getById(id);
    }
}
