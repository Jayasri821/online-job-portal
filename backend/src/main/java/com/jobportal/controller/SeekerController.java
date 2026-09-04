package com.jobportal.controller;

import com.jobportal.dto.ApplicationDto;
import com.jobportal.dto.ApplyRequest;
import com.jobportal.dto.ProfileDto;
import com.jobportal.dto.ProfileUpdateRequest;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/seeker")
public class SeekerController {

    private final ProfileService profileService;
    private final ApplicationService applicationService;

    public SeekerController(ProfileService profileService, ApplicationService applicationService) {
        this.profileService = profileService;
        this.applicationService = applicationService;
    }

    @GetMapping("/profile")
    public ProfileDto profile() {
        return profileService.getMyProfile();
    }

    @PutMapping("/profile")
    public ProfileDto updateProfile(@RequestBody ProfileUpdateRequest request) {
        return profileService.updateMyProfile(request);
    }

    @PostMapping("/resume")
    public ProfileDto uploadResume(@RequestParam("file") MultipartFile file) {
        return profileService.uploadResume(file);
    }

    @PostMapping("/apply")
    public ApplicationDto apply(@Valid @RequestBody ApplyRequest request) {
        return applicationService.apply(request);
    }

    @GetMapping("/applications")
    public List<ApplicationDto> applications() {
        return applicationService.myApplications();
    }
}
