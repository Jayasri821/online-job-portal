package com.jobportal.service;

import com.jobportal.dto.ApplicationDto;
import com.jobportal.dto.ApplyRequest;
import com.jobportal.dto.InterviewRequest;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Interview;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.entity.JobStatus;
import com.jobportal.entity.User;
import com.jobportal.exception.ApiException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.InterviewRepository;
import com.jobportal.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final JobService jobService;
    private final ProfileService profileService;
    private final RecruiterService recruiterService;
    private final CurrentUserService currentUserService;

    public ApplicationService(
            JobApplicationRepository applicationRepository,
            InterviewRepository interviewRepository,
            JobService jobService,
            ProfileService profileService,
            RecruiterService recruiterService,
            CurrentUserService currentUserService
    ) {
        this.applicationRepository = applicationRepository;
        this.interviewRepository = interviewRepository;
        this.jobService = jobService;
        this.profileService = profileService;
        this.recruiterService = recruiterService;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public ApplicationDto apply(ApplyRequest request) {
        User seeker = currentUserService.requireUser();
        profileService.requireResume(seeker);
        Job job = jobService.getEntity(request.jobId());
        if (job.getStatus() != JobStatus.OPEN) {
            throw new ApiException("This job is no longer open", 400);
        }
        if (applicationRepository.existsByJobAndSeeker(job, seeker)) {
            throw new ApiException("You already applied for this job", 409);
        }
        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setSeeker(seeker);
        application.setCoverLetter(request.coverLetter());
        application.setStatus(ApplicationStatus.APPLIED);
        return DtoMapper.toApplication(applicationRepository.save(application));
    }

    public List<ApplicationDto> myApplications() {
        User seeker = currentUserService.requireUser();
        return applicationRepository.findBySeekerOrderByAppliedAtDesc(seeker).stream()
                .map(DtoMapper::toApplication)
                .toList();
    }

    public List<ApplicationDto> applicantsForJob(Long jobId) {
        Job job = recruiterService.requireOwnedJob(jobId);
        return applicationRepository.findByJobOrderByAppliedAtDesc(job).stream()
                .map(DtoMapper::toApplication)
                .toList();
    }

    @Transactional
    public ApplicationDto updateStatus(Long applicationId, ApplicationStatus status) {
        JobApplication application = getOwnedApplication(applicationId);
        if (status == ApplicationStatus.INTERVIEW_SCHEDULED) {
            throw new ApiException("Use the interview endpoint to schedule interviews", 400);
        }
        application.setStatus(status);
        return DtoMapper.toApplication(applicationRepository.save(application));
    }

    @Transactional
    public ApplicationDto scheduleInterview(Long applicationId, InterviewRequest request) {
        JobApplication application = getOwnedApplication(applicationId);
        Interview interview = interviewRepository.findByApplication(application).orElseGet(Interview::new);
        interview.setApplication(application);
        interview.setScheduledAt(request.scheduledAt());
        interview.setMode(request.mode());
        interview.setMeetingLink(request.meetingLink());
        interview.setNotes(request.notes());
        interviewRepository.save(interview);
        application.setInterview(interview);
        application.setStatus(ApplicationStatus.INTERVIEW_SCHEDULED);
        return DtoMapper.toApplication(applicationRepository.save(application));
    }

    private JobApplication getOwnedApplication(Long applicationId) {
        JobApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        recruiterService.requireOwnedJob(application.getJob().getId());
        return application;
    }
}
