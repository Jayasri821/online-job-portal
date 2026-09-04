package com.careermatch.service;

import com.careermatch.dto.JobDto;
import com.careermatch.dto.JobMatchResultDto;
import com.careermatch.entity.Job;
import com.careermatch.entity.JobSeekerProfile;
import com.careermatch.entity.JobStatus;
import com.careermatch.entity.User;
import com.careermatch.repository.JobApplicationRepository;
import com.careermatch.repository.JobRepository;
import com.careermatch.repository.JobSeekerProfileRepository;
import com.careermatch.repository.SavedJobRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final JobRepository jobRepository;
    private final JobSeekerProfileRepository profileRepository;
    private final JobApplicationRepository applicationRepository;
    private final SavedJobRepository savedJobRepository;
    private final JobMatchingService jobMatchingService;
    private final DtoMapper dtoMapper;

    public RecommendationService(JobRepository jobRepository,
                                 JobSeekerProfileRepository profileRepository,
                                 JobApplicationRepository applicationRepository,
                                 SavedJobRepository savedJobRepository,
                                 JobMatchingService jobMatchingService,
                                 DtoMapper dtoMapper) {
        this.jobRepository = jobRepository;
        this.profileRepository = profileRepository;
        this.applicationRepository = applicationRepository;
        this.savedJobRepository = savedJobRepository;
        this.jobMatchingService = jobMatchingService;
        this.dtoMapper = dtoMapper;
    }

    public List<JobDto> getRecommendationsForUser(User user, int limit) {
        JobSeekerProfile profile = profileRepository.findByUser(user).orElse(null);
        List<Job> allOpenJobs = jobRepository.findByStatus(JobStatus.OPEN);

        Set<Long> appliedJobIds = applicationRepository.findByCandidateOrderByAppliedAtDesc(user)
                .stream().map(a -> a.getJob().getId()).collect(Collectors.toSet());

        Set<Long> savedJobIds = savedJobRepository.findByCandidateOrderBySavedAtDesc(user)
                .stream().map(s -> s.getJob().getId()).collect(Collectors.toSet());

        return allOpenJobs.stream()
                .filter(job -> !appliedJobIds.contains(job.getId()))
                .map(job -> {
                    JobMatchResultDto matchResult = jobMatchingService.calculateMatch(profile, job);
                    JobDto dto = dtoMapper.toJobDto(job, profile, savedJobIds.contains(job.getId()), false, null);
                    dto.setMatchScore(matchResult.getOverallMatchScore());
                    dto.setMatchedSkills(matchResult.getMatchedSkills());
                    dto.setMissingSkills(matchResult.getMissingSkills());
                    dto.setWhyThisJob(matchResult.getWhyThisJobExplanations());
                    return dto;
                })
                .sorted(Comparator.comparing(JobDto::getMatchScore).reversed()
                        .thenComparing(dto -> dto.getDaysLeft() != null ? dto.getDaysLeft() : Long.MAX_VALUE))
                .limit(limit > 0 ? limit : 20)
                .collect(Collectors.toList());
    }
}
