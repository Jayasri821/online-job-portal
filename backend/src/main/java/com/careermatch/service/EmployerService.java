package com.careermatch.service;

import com.careermatch.dto.*;
import com.careermatch.entity.*;
import com.careermatch.exception.ApiException;
import com.careermatch.exception.ResourceNotFoundException;
import com.careermatch.exception.UnauthorizedException;
import com.careermatch.repository.EmployerProfileRepository;
import com.careermatch.repository.JobApplicationRepository;
import com.careermatch.repository.JobRepository;
import com.careermatch.repository.JobSeekerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployerService {

    private final EmployerProfileRepository employerProfileRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;
    private final JobSeekerProfileRepository seekerProfileRepository;
    private final JobMatchingService jobMatchingService;
    private final DtoMapper dtoMapper;

    public EmployerService(EmployerProfileRepository employerProfileRepository,
                           JobRepository jobRepository,
                           JobApplicationRepository applicationRepository,
                           JobSeekerProfileRepository seekerProfileRepository,
                           JobMatchingService jobMatchingService,
                           DtoMapper dtoMapper) {
        this.employerProfileRepository = employerProfileRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.seekerProfileRepository = seekerProfileRepository;
        this.jobMatchingService = jobMatchingService;
        this.dtoMapper = dtoMapper;
    }

    public EmployerProfile getEmployerProfile(User user) {
        return employerProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Employer profile not found for user: " + user.getEmail()));
    }

    public EmployerProfileDto getProfileDto(User user) {
        return dtoMapper.toEmployerDto(getEmployerProfile(user));
    }

    @Transactional
    public EmployerProfileDto updateProfile(User user, EmployerProfileDto dto) {
        EmployerProfile profile = getEmployerProfile(user);
        if (dto.getCompanyName() != null) profile.setCompanyName(dto.getCompanyName());
        if (dto.getIndustry() != null) profile.setIndustry(dto.getIndustry());
        if (dto.getLocation() != null) profile.setLocation(dto.getLocation());
        if (dto.getWebsite() != null) profile.setWebsite(dto.getWebsite());
        if (dto.getDescription() != null) profile.setDescription(dto.getDescription());
        if (dto.getLogoUrl() != null) profile.setLogoUrl(dto.getLogoUrl());

        profile = employerProfileRepository.save(profile);
        return dtoMapper.toEmployerDto(profile);
    }

    @Transactional
    public JobDto postJob(User user, JobRequest request) {
        EmployerProfile profile = getEmployerProfile(user);

        Job job = new Job();
        job.setEmployer(profile);
        job.setTitle(request.getTitle().trim());
        job.setDescription(request.getDescription().trim());
        job.setResponsibilities(request.getResponsibilities());
        job.setQualifications(request.getQualifications());
        job.setRequiredSkills(request.getRequiredSkills().trim());
        job.setLocation(request.getLocation());
        job.setExperienceYears(request.getExperienceYears() != null ? request.getExperienceYears() : 0);
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setJobType(request.getJobType());
        job.setWorkMode(request.getWorkMode());
        job.setApplicationDeadline(request.getApplicationDeadline());
        job.setStatus(JobStatus.OPEN);

        job = jobRepository.save(job);
        return dtoMapper.toJobDto(job, null, false, false, null);
    }

    @Transactional
    public JobDto updateJob(Long jobId, JobRequest request, User user) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        EmployerProfile profile = getEmployerProfile(user);
        if (!job.getEmployer().getId().equals(profile.getId()) && user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("You are not authorized to edit this job.");
        }

        job.setTitle(request.getTitle().trim());
        job.setDescription(request.getDescription().trim());
        job.setResponsibilities(request.getResponsibilities());
        job.setQualifications(request.getQualifications());
        job.setRequiredSkills(request.getRequiredSkills().trim());
        job.setLocation(request.getLocation());
        job.setExperienceYears(request.getExperienceYears());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setJobType(request.getJobType());
        job.setWorkMode(request.getWorkMode());
        job.setApplicationDeadline(request.getApplicationDeadline());

        job = jobRepository.save(job);
        return dtoMapper.toJobDto(job, null, false, false, null);
    }

    @Transactional
    public void deleteJob(Long jobId, User user) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        EmployerProfile profile = getEmployerProfile(user);
        if (!job.getEmployer().getId().equals(profile.getId()) && user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("You are not authorized to delete this job.");
        }

        jobRepository.delete(job);
    }

    public List<JobDto> getEmployerJobs(User user) {
        EmployerProfile profile = getEmployerProfile(user);
        List<Job> jobs = jobRepository.findByEmployer(profile);
        return jobs.stream()
                .map(j -> dtoMapper.toJobDto(j, null, false, false, null))
                .sorted(Comparator.comparing(JobDto::getPostedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public List<ApplicationDto> getApplicantsForJob(Long jobId, User user) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        EmployerProfile profile = getEmployerProfile(user);
        if (!job.getEmployer().getId().equals(profile.getId()) && user.getRole() != Role.ADMIN) {
            throw new UnauthorizedException("You are not authorized to view applicants for this job.");
        }

        List<JobApplication> applications = applicationRepository.findByJobOrderByAppliedAtDesc(job);

        return applications.stream()
                .map(app -> {
                    JobSeekerProfile seekerProfile = seekerProfileRepository.findByUser(app.getCandidate()).orElse(null);
                    ApplicationDto dto = dtoMapper.toApplicationDto(app, seekerProfile);
                    // Rank dynamically using matching service
                    if (seekerProfile != null) {
                        JobMatchResultDto matchResult = jobMatchingService.calculateMatch(seekerProfile, job);
                        dto.setMatchScore((double) matchResult.getOverallMatchScore());
                    }
                    return dto;
                })
                .sorted(Comparator.comparing(ApplicationDto::getMatchScore, Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());
    }

    public DashboardStatsDto getEmployerDashboardStats(User user) {
        EmployerProfile profile = getEmployerProfile(user);
        List<Job> jobs = jobRepository.findByEmployer(profile);
        List<JobApplication> applications = applicationRepository.findByJobEmployerIdOrderByAppliedAtDesc(profile.getId());

        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setTotalJobsPosted((long) jobs.size());
        stats.setActiveJobsCount(jobs.stream().filter(j -> j.getStatus() == JobStatus.OPEN).count());
        stats.setTotalApplicantsReceived((long) applications.size());
        stats.setShortlistedCandidates(applications.stream().filter(a -> a.getStatus() == ApplicationStatus.SHORTLISTED).count());
        stats.setEmployerInterviewsCount(applications.stream().filter(a -> a.getStatus() == ApplicationStatus.INTERVIEW).count());
        stats.setSelectedCandidates(applications.stream().filter(a -> a.getStatus() == ApplicationStatus.SELECTED).count());

        return stats;
    }
}
