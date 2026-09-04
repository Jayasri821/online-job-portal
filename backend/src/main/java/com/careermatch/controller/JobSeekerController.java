package com.careermatch.controller;

import com.careermatch.dto.ApiResponse;
import com.careermatch.dto.CareerReadinessDto;
import com.careermatch.dto.DashboardStatsDto;
import com.careermatch.dto.JobSeekerProfileDto;
import com.careermatch.entity.ApplicationStatus;
import com.careermatch.entity.JobSeekerProfile;
import com.careermatch.entity.PracticeStatus;
import com.careermatch.entity.User;
import com.careermatch.repository.InterviewProgressRepository;
import com.careermatch.repository.JobApplicationRepository;
import com.careermatch.repository.JobSeekerProfileRepository;
import com.careermatch.repository.SavedJobRepository;
import com.careermatch.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/seeker")
public class JobSeekerController {

    private final ProfileService profileService;
    private final CareerScoreService careerScoreService;
    private final RecommendationService recommendationService;
    private final FileStorageService fileStorageService;
    private final CurrentUserService currentUserService;
    private final JobSeekerProfileRepository seekerProfileRepository;
    private final JobApplicationRepository applicationRepository;
    private final SavedJobRepository savedJobRepository;
    private final InterviewProgressRepository interviewProgressRepository;

    public JobSeekerController(ProfileService profileService,
                               CareerScoreService careerScoreService,
                               RecommendationService recommendationService,
                               FileStorageService fileStorageService,
                               CurrentUserService currentUserService,
                               JobSeekerProfileRepository seekerProfileRepository,
                               JobApplicationRepository applicationRepository,
                               SavedJobRepository savedJobRepository,
                               InterviewProgressRepository interviewProgressRepository) {
        this.profileService = profileService;
        this.careerScoreService = careerScoreService;
        this.recommendationService = recommendationService;
        this.fileStorageService = fileStorageService;
        this.currentUserService = currentUserService;
        this.seekerProfileRepository = seekerProfileRepository;
        this.applicationRepository = applicationRepository;
        this.savedJobRepository = savedJobRepository;
        this.interviewProgressRepository = interviewProgressRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<JobSeekerProfileDto>> getProfile() {
        User user = currentUserService.getCurrentUser();
        JobSeekerProfileDto profile = profileService.getProfileForUser(user);
        return ResponseEntity.ok(ApiResponse.ok("Candidate profile", profile));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<JobSeekerProfileDto>> updateProfile(@RequestBody JobSeekerProfileDto dto) {
        User user = currentUserService.getCurrentUser();
        JobSeekerProfileDto updated = profileService.updateProfile(user, dto);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated successfully", updated));
    }

    @GetMapping("/career-readiness")
    public ResponseEntity<ApiResponse<CareerReadinessDto>> getCareerReadiness() {
        User user = currentUserService.getCurrentUser();
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user).orElse(null);
        CareerReadinessDto score = careerScoreService.calculateCareerReadiness(profile);
        return ResponseEntity.ok(ApiResponse.ok("Career readiness score & breakdown", score));
    }

    @PostMapping("/resume/upload")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadResume(@RequestParam("file") MultipartFile file) {
        User user = currentUserService.getCurrentUser();
        String fileUrl = fileStorageService.storeFile(file);

        JobSeekerProfile profile = seekerProfileRepository.findByUser(user)
                .orElseGet(() -> {
                    JobSeekerProfile p = new JobSeekerProfile();
                    p.setUser(user);
                    return p;
                });
        profile.setResumeUrl(fileUrl);
        profile.setResumeOriginalName(file.getOriginalFilename());
        seekerProfileRepository.save(profile);

        Map<String, String> response = new HashMap<>();
        response.put("resumeUrl", fileUrl);
        response.put("fileName", file.getOriginalFilename());

        return ResponseEntity.ok(ApiResponse.ok("Resume uploaded successfully", response));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getDashboardStats() {
        User user = currentUserService.getCurrentUser();
        JobSeekerProfile profile = seekerProfileRepository.findByUser(user).orElse(null);
        CareerReadinessDto readiness = careerScoreService.calculateCareerReadiness(profile);

        long recommendedCount = recommendationService.getRecommendationsForUser(user, 100).size();
        long totalApps = applicationRepository.countByCandidateId(user.getId());
        long savedCount = savedJobRepository.findByCandidateIdOrderBySavedAtDesc(user.getId()).size();

        var apps = applicationRepository.findByCandidateOrderByAppliedAtDesc(user);
        long interviews = apps.stream().filter(a -> a.getStatus() == ApplicationStatus.INTERVIEW).count();
        long shortlisted = apps.stream().filter(a -> a.getStatus() == ApplicationStatus.SHORTLISTED).count();
        long selected = apps.stream().filter(a -> a.getStatus() == ApplicationStatus.SELECTED).count();
        long rejected = apps.stream().filter(a -> a.getStatus() == ApplicationStatus.REJECTED).count();

        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setCareerReadinessScore(readiness.getOverallScore());
        stats.setRecommendedJobsCount(recommendedCount);
        stats.setTotalApplicationsCount(totalApps);
        stats.setSavedJobsCount(savedCount);
        stats.setInterviewsCount(interviews);
        stats.setShortlistedCount(shortlisted);
        stats.setSelectedCount(selected);
        stats.setRejectedCount(rejected);
        stats.setTopSkillToImprove(readiness.getTopSkillGap());

        return ResponseEntity.ok(ApiResponse.ok("Job seeker dashboard stats", stats));
    }
}
