package com.careermatch.controller;

import com.careermatch.dto.ApiResponse;
import com.careermatch.dto.InterviewQuestionDto;
import com.careermatch.dto.QuestionProgressRequest;
import com.careermatch.entity.User;
import com.careermatch.service.CurrentUserService;
import com.careermatch.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    private final InterviewService interviewService;
    private final CurrentUserService currentUserService;

    public InterviewController(InterviewService interviewService, CurrentUserService currentUserService) {
        this.interviewService = interviewService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getInterviewKit(@PathVariable Long jobId) {
        User user = currentUserService.getCurrentUserOptional().orElse(null);
        Map<String, Object> kit = interviewService.getInterviewKitForJob(jobId, user);
        return ResponseEntity.ok(ApiResponse.ok("Interview preparation kit", kit));
    }

    @PostMapping("/progress")
    public ResponseEntity<ApiResponse<InterviewQuestionDto>> updateProgress(@Valid @RequestBody QuestionProgressRequest request) {
        User user = currentUserService.getCurrentUser();
        InterviewQuestionDto updated = interviewService.updateQuestionProgress(user, request);
        return ResponseEntity.ok(ApiResponse.ok("Question progress updated", updated));
    }
}
