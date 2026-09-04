package com.careermatch.controller;

import com.careermatch.dto.*;
import com.careermatch.entity.Job;
import com.careermatch.entity.JobSeekerProfile;
import com.careermatch.entity.JobType;
import com.careermatch.entity.User;
import com.careermatch.entity.WorkMode;
import com.careermatch.exception.ResourceNotFoundException;
import com.careermatch.repository.JobRepository;
import com.careermatch.repository.JobSeekerProfileRepository;
import com.careermatch.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;
    private final JobMatchingService jobMatchingService;
    private final SkillGapService skillGapService;
    private final ApplicationStrengthService strengthService;
    private final RecommendationService recommendationService;
    private final CurrentUserService currentUserService;
    private final JobRepository jobRepository;
    private final JobSeekerProfileRepository seekerProfileRepository;

    public JobController(JobService jobService,
                         JobMatchingService jobMatchingService,
                         SkillGapService skillGapService,
                         ApplicationStrengthService strengthService,
                         RecommendationService recommendationService,
                         CurrentUserService currentUserService,
                         JobRepository jobRepository,
                         JobSeekerProfileRepository seekerProfileRepository) {
        this.jobService = jobService;
        this.jobMatchingService = jobMatchingService;
        this.skillGapService = skillGapService;
        this.strengthService = strengthService;
        this.recommendationService = recommendationService;
        this.currentUserService = currentUserService;
        this.jobRepository = jobRepository;
        this.seekerProfileRepository = seekerProfileRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobDto>>> searchJobs(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) WorkMode workMode,
            @RequestParam(required = false) JobType jobType,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer maxExp,
            @RequestParam(required = false, defaultValue = "latest") String sort
    ) {
        User user = currentUserService.getCurrentUserOptional().orElse(null);
        List<JobDto> results = jobService.searchJobs(q, location, workMode, jobType, minSalary, maxExp, sort, user);
        return ResponseEntity.ok(ApiResponse.ok("Jobs retrieved successfully", results));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDto>> getJobById(@PathVariable Long id) {
        User user = currentUserService.getCurrentUserOptional().orElse(null);
        JobDto job = jobService.getJobById(id, user);
        return ResponseEntity.ok(ApiResponse.ok("Job details", job));
    }

    @GetMapping("/{id}/match")
    public ResponseEntity<ApiResponse<JobMatchResultDto>> getJobMatchScore(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        User user = currentUserService.getCurrentUser();
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user).orElse(null);

        JobMatchResultDto matchResult = jobMatchingService.calculateMatch(profile, job);
        return ResponseEntity.ok(ApiResponse.ok("Job match calculation", matchResult));
    }

    @GetMapping("/{id}/skill-gap")
    public ResponseEntity<ApiResponse<SkillGapDto>> getSkillGap(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        User user = currentUserService.getCurrentUser();
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user).orElse(null);

        SkillGapDto skillGap = skillGapService.analyzeSkillGap(profile, job);
        return ResponseEntity.ok(ApiResponse.ok("Skill gap analysis", skillGap));
    }

    @GetMapping("/{id}/why-recommended")
    public ResponseEntity<ApiResponse<List<String>>> getWhyRecommended(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        User user = currentUserService.getCurrentUser();
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user).orElse(null);

        JobMatchResultDto matchResult = jobMatchingService.calculateMatch(profile, job);
        return ResponseEntity.ok(ApiResponse.ok("Why this job explanations", matchResult.getWhyThisJobExplanations()));
    }

    @GetMapping("/{id}/strength")
    public ResponseEntity<ApiResponse<ApplicationStrengthDto>> getApplicationStrength(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        User user = currentUserService.getCurrentUser();
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user).orElse(null);

        ApplicationStrengthDto strength = strengthService.evaluateStrength(profile, job);
        return ResponseEntity.ok(ApiResponse.ok("Application strength evaluation", strength));
    }

    @GetMapping("/recommended")
    public ResponseEntity<ApiResponse<List<JobDto>>> getRecommendedJobs(@RequestParam(defaultValue = "10") int limit) {
        User user = currentUserService.getCurrentUser();
        List<JobDto> recommended = recommendationService.getRecommendationsForUser(user, limit);
        return ResponseEntity.ok(ApiResponse.ok("Recommended jobs for you", recommended));
    }

    @PostMapping("/compare")
    public ResponseEntity<ApiResponse<JobComparisonDto>> compareJobs(@RequestBody List<Long> jobIds) {
        User user = currentUserService.getCurrentUserOptional().orElse(null);
        JobComparisonDto comparison = jobService.compareJobs(jobIds, user);
        return ResponseEntity.ok(ApiResponse.ok("Job comparison result", comparison));
    }

    @PostMapping("/{id}/save")
    public ResponseEntity<ApiResponse<String>> saveJob(@PathVariable Long id) {
        User user = currentUserService.getCurrentUser();
        jobService.saveJob(user, id);
        return ResponseEntity.ok(ApiResponse.ok("Job saved successfully", "Job #" + id + " saved"));
    }

    @DeleteMapping("/{id}/save")
    public ResponseEntity<ApiResponse<String>> unsaveJob(@PathVariable Long id) {
        User user = currentUserService.getCurrentUser();
        jobService.unsaveJob(user, id);
        return ResponseEntity.ok(ApiResponse.ok("Job removed from saved list", "Job #" + id + " unsaved"));
    }

    @GetMapping("/saved")
    public ResponseEntity<ApiResponse<List<JobDto>>> getSavedJobs() {
        User user = currentUserService.getCurrentUser();
        List<JobDto> saved = jobService.getSavedJobs(user);
        return ResponseEntity.ok(ApiResponse.ok("Saved jobs list", saved));
    }
}
