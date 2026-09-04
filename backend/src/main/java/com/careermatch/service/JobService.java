package com.careermatch.service;

import com.careermatch.dto.*;
import com.careermatch.entity.*;
import com.careermatch.exception.ApiException;
import com.careermatch.exception.ResourceNotFoundException;
import com.careermatch.repository.JobApplicationRepository;
import com.careermatch.repository.JobRepository;
import com.careermatch.repository.JobSeekerProfileRepository;
import com.careermatch.repository.SavedJobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobSeekerProfileRepository profileRepository;
    private final SavedJobRepository savedJobRepository;
    private final JobApplicationRepository applicationRepository;
    private final JobMatchingService jobMatchingService;
    private final DtoMapper dtoMapper;

    public JobService(JobRepository jobRepository,
                      JobSeekerProfileRepository profileRepository,
                      SavedJobRepository savedJobRepository,
                      JobApplicationRepository applicationRepository,
                      JobMatchingService jobMatchingService,
                      DtoMapper dtoMapper) {
        this.jobRepository = jobRepository;
        this.profileRepository = profileRepository;
        this.savedJobRepository = savedJobRepository;
        this.applicationRepository = applicationRepository;
        this.jobMatchingService = jobMatchingService;
        this.dtoMapper = dtoMapper;
    }

    public List<JobDto> searchJobs(String query, String location, WorkMode workMode,
                                  JobType jobType, Integer minSalary, Integer maxExp,
                                  String sort, User currentUser) {
        List<Job> jobs;
        if (query != null && !query.trim().isEmpty()) {
            jobs = jobRepository.searchOpenJobs(query.trim());
        } else {
            jobs = jobRepository.findByStatus(JobStatus.OPEN);
        }

        JobSeekerProfile candidateProfile = null;
        Set<Long> savedIds = new HashSet<>();
        Set<Long> appliedIds = new HashSet<>();
        Map<Long, String> appStatusMap = new HashMap<>();

        if (currentUser != null && currentUser.getRole() == Role.JOB_SEEKER) {
            candidateProfile = profileRepository.findByUser(currentUser).orElse(null);
            savedIds = savedJobRepository.findByCandidateOrderBySavedAtDesc(currentUser)
                    .stream().map(s -> s.getJob().getId()).collect(Collectors.toSet());
            List<JobApplication> apps = applicationRepository.findByCandidateOrderByAppliedAtDesc(currentUser);
            for (JobApplication a : apps) {
                appliedIds.add(a.getJob().getId());
                appStatusMap.put(a.getJob().getId(), a.getStatus().name());
            }
        }

        final JobSeekerProfile profileFinal = candidateProfile;
        final Set<Long> finalSavedIds = savedIds;
        final Set<Long> finalAppliedIds = appliedIds;

        List<JobDto> dtos = jobs.stream()
                .filter(j -> location == null || location.isEmpty() || (j.getLocation() != null && j.getLocation().toLowerCase().contains(location.toLowerCase())))
                .filter(j -> workMode == null || j.getWorkMode() == workMode)
                .filter(j -> jobType == null || j.getJobType() == jobType)
                .filter(j -> minSalary == null || (j.getSalaryMax() != null && j.getSalaryMax() >= minSalary))
                .filter(j -> maxExp == null || (j.getExperienceYears() != null && j.getExperienceYears() <= maxExp))
                .map(j -> dtoMapper.toJobDto(j, profileFinal, finalSavedIds.contains(j.getId()), finalAppliedIds.contains(j.getId()), appStatusMap.get(j.getId())))
                .collect(Collectors.toList());

        // Apply Sorting
        if ("best_match".equalsIgnoreCase(sort) && profileFinal != null) {
            dtos.sort(Comparator.comparing(JobDto::getMatchScore, Comparator.nullsLast(Comparator.reverseOrder())));
        } else if ("closing_soon".equalsIgnoreCase(sort)) {
            dtos.sort(Comparator.comparing(dto -> dto.getDaysLeft() != null ? dto.getDaysLeft() : Long.MAX_VALUE));
        } else if ("highest_salary".equalsIgnoreCase(sort)) {
            dtos.sort(Comparator.comparing(JobDto::getSalaryMax, Comparator.nullsLast(Comparator.reverseOrder())));
        } else {
            // Default latest
            dtos.sort(Comparator.comparing(JobDto::getPostedAt, Comparator.nullsLast(Comparator.reverseOrder())));
        }

        return dtos;
    }

    public JobDto getJobById(Long id, User currentUser) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        JobSeekerProfile candidateProfile = null;
        boolean isSaved = false;
        boolean hasApplied = false;
        String appStatus = null;

        if (currentUser != null && currentUser.getRole() == Role.JOB_SEEKER) {
            candidateProfile = profileRepository.findByUser(currentUser).orElse(null);
            isSaved = savedJobRepository.existsByCandidateIdAndJobId(currentUser.getId(), id);
            Optional<JobApplication> app = applicationRepository.findByCandidateAndJob(currentUser, job);
            if (app.isPresent()) {
                hasApplied = true;
                appStatus = app.get().getStatus().name();
            }
        }

        return dtoMapper.toJobDto(job, candidateProfile, isSaved, hasApplied, appStatus);
    }

    public JobComparisonDto compareJobs(List<Long> jobIds, User currentUser) {
        if (jobIds == null || jobIds.isEmpty()) {
            throw new ApiException("Please select at least one job to compare.");
        }

        final JobSeekerProfile finalProfile = (currentUser != null && currentUser.getRole() == Role.JOB_SEEKER)
                ? profileRepository.findByUser(currentUser).orElse(null)
                : null;

        List<JobDto> dtoList = new ArrayList<>();
        for (Long jid : jobIds) {
            jobRepository.findById(jid).ifPresent(j -> {
                dtoList.add(dtoMapper.toJobDto(j, finalProfile, false, false, null));
            });
        }

        JobComparisonDto comparison = new JobComparisonDto();
        comparison.setJobs(dtoList);
        if (currentUser != null) comparison.setCandidateId(currentUser.getId());

        if (!dtoList.isEmpty()) {
            JobDto bestMatch = dtoList.stream()
                    .max(Comparator.comparing(JobDto::getMatchScore, Comparator.nullsLast(Comparator.naturalOrder())))
                    .orElse(dtoList.get(0));
            comparison.setBestMatchJobId(bestMatch.getId());
            comparison.setBestMatchJobTitle(bestMatch.getTitle());

            JobDto highestSal = dtoList.stream()
                    .max(Comparator.comparing(JobDto::getSalaryMax, Comparator.nullsLast(Comparator.naturalOrder())))
                    .orElse(dtoList.get(0));
            comparison.setHighestSalaryJobId(highestSal.getId());
            comparison.setHighestSalaryJobTitle(highestSal.getTitle());
        }

        return comparison;
    }

    @Transactional
    public void saveJob(User candidate, Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!savedJobRepository.existsByCandidateAndJob(candidate, job)) {
            SavedJob savedJob = new SavedJob();
            savedJob.setCandidate(candidate);
            savedJob.setJob(job);
            savedJobRepository.save(savedJob);
        }
    }

    @Transactional
    public void unsaveJob(User candidate, Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        savedJobRepository.deleteByCandidateAndJob(candidate, job);
    }

    public List<JobDto> getSavedJobs(User candidate) {
        JobSeekerProfile profile = profileRepository.findByUser(candidate).orElse(null);
        List<SavedJob> savedList = savedJobRepository.findByCandidateOrderBySavedAtDesc(candidate);
        return savedList.stream()
                .map(s -> dtoMapper.toJobDto(s.getJob(), profile, true, false, null))
                .collect(Collectors.toList());
    }
}
