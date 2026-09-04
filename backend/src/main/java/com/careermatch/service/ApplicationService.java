package com.careermatch.service;

import com.careermatch.dto.ApplicationDto;
import com.careermatch.dto.ApplyRequest;
import com.careermatch.dto.JobMatchResultDto;
import com.careermatch.dto.StatusUpdateRequest;
import com.careermatch.entity.*;
import com.careermatch.exception.ApiException;
import com.careermatch.exception.ResourceNotFoundException;
import com.careermatch.exception.UnauthorizedException;
import com.careermatch.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final JobSeekerProfileRepository seekerProfileRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final NotificationRepository notificationRepository;
    private final JobMatchingService jobMatchingService;
    private final DtoMapper dtoMapper;

    public ApplicationService(JobApplicationRepository applicationRepository,
                              JobRepository jobRepository,
                              JobSeekerProfileRepository seekerProfileRepository,
                              EmployerProfileRepository employerProfileRepository,
                              NotificationRepository notificationRepository,
                              JobMatchingService jobMatchingService,
                              DtoMapper dtoMapper) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.seekerProfileRepository = seekerProfileRepository;
        this.employerProfileRepository = employerProfileRepository;
        this.notificationRepository = notificationRepository;
        this.jobMatchingService = jobMatchingService;
        this.dtoMapper = dtoMapper;
    }

    @Transactional
    public ApplicationDto applyForJob(User candidate, ApplyRequest request) {
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + request.getJobId()));

        if (job.getStatus() != JobStatus.OPEN) {
            throw new ApiException("This job opening is currently not accepting applications.");
        }

        if (job.getApplicationDeadline() != null && job.getApplicationDeadline().isBefore(LocalDate.now())) {
            throw new ApiException("The application deadline for this job has expired.");
        }

        if (applicationRepository.existsByCandidateAndJob(candidate, job)) {
            throw new ApiException("You have already submitted an application for this position.");
        }

        JobSeekerProfile profile = seekerProfileRepository.findByUser(candidate).orElse(null);
        JobMatchResultDto matchResult = jobMatchingService.calculateMatch(profile, job);

        String resume = request.getResumeUrl();
        if (resume == null || resume.isEmpty()) {
            if (profile != null) {
                resume = profile.getResumeUrl();
            }
        }

        JobApplication application = new JobApplication();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setResumeUrl(resume);
        application.setCoverLetter(request.getCoverLetter());
        application.setMatchScoreAtApplication((double) matchResult.getOverallMatchScore());

        application = applicationRepository.save(application);

        // Notify employer
        if (job.getEmployer() != null && job.getEmployer().getUser() != null) {
            Notification notif = new Notification();
            notif.setUser(job.getEmployer().getUser());
            notif.setTitle("New Applicant: " + job.getTitle());
            notif.setMessage(candidate.getFullName() + " applied for " + job.getTitle() + " (Match: " + matchResult.getOverallMatchScore() + "%)");
            notif.setType("APPLICATION_RECEIVED");
            notif.setReferenceId(application.getId());
            notificationRepository.save(notif);
        }

        return dtoMapper.toApplicationDto(application, profile);
    }

    public List<ApplicationDto> getCandidateApplications(User candidate) {
        JobSeekerProfile profile = seekerProfileRepository.findByUser(candidate).orElse(null);
        List<JobApplication> apps = applicationRepository.findByCandidateOrderByAppliedAtDesc(candidate);
        return apps.stream()
                .map(a -> dtoMapper.toApplicationDto(a, profile))
                .collect(Collectors.toList());
    }

    public ApplicationDto getApplicationById(Long id, User currentUser) {
        JobApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        JobSeekerProfile profile = seekerProfileRepository.findByUser(app.getCandidate()).orElse(null);

        // Access check
        if (currentUser.getRole() == Role.JOB_SEEKER && !app.getCandidate().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You cannot view this application.");
        }
        if (currentUser.getRole() == Role.EMPLOYER) {
            EmployerProfile emp = employerProfileRepository.findByUser(currentUser).orElse(null);
            if (emp == null || !app.getJob().getEmployer().getId().equals(emp.getId())) {
                throw new UnauthorizedException("You cannot view applications for jobs from other employers.");
            }
        }

        return dtoMapper.toApplicationDto(app, profile);
    }

    @Transactional
    public ApplicationDto updateApplicationStatus(Long id, StatusUpdateRequest request, User currentUser) {
        JobApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        // Employer check
        if (currentUser.getRole() == Role.EMPLOYER) {
            EmployerProfile emp = employerProfileRepository.findByUser(currentUser)
                    .orElseThrow(() -> new UnauthorizedException("Employer profile not found"));
            if (!app.getJob().getEmployer().getId().equals(emp.getId())) {
                throw new UnauthorizedException("You can only update applications for your own job listings.");
            }
        }

        app.setStatus(request.getStatus());
        if (request.getRecruiterNotes() != null) {
            app.setRecruiterNotes(request.getRecruiterNotes());
        }

        app = applicationRepository.save(app);

        // Create notification for candidate
        Notification notif = new Notification();
        notif.setUser(app.getCandidate());
        notif.setTitle("Application Update: " + app.getJob().getTitle());
        notif.setMessage("Your application status for " + app.getJob().getTitle() + " at " + app.getJob().getEmployer().getCompanyName() + " was updated to " + request.getStatus().name());
        notif.setType("APPLICATION_STATUS");
        notif.setReferenceId(app.getId());
        notificationRepository.save(notif);

        JobSeekerProfile profile = seekerProfileRepository.findByUser(app.getCandidate()).orElse(null);
        return dtoMapper.toApplicationDto(app, profile);
    }
}
