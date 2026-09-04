package com.careermatch.service;

import com.careermatch.dto.ApplicationDto;
import com.careermatch.dto.DashboardStatsDto;
import com.careermatch.dto.JobDto;
import com.careermatch.dto.UserDto;
import com.careermatch.entity.*;
import com.careermatch.exception.ResourceNotFoundException;
import com.careermatch.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;
    private final EmployerProfileRepository employerProfileRepository;
    private final JobSeekerProfileRepository seekerProfileRepository;
    private final DtoMapper dtoMapper;

    public AdminService(UserRepository userRepository,
                        JobRepository jobRepository,
                        JobApplicationRepository applicationRepository,
                        EmployerProfileRepository employerProfileRepository,
                        JobSeekerProfileRepository seekerProfileRepository,
                        DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.employerProfileRepository = employerProfileRepository;
        this.seekerProfileRepository = seekerProfileRepository;
        this.dtoMapper = dtoMapper;
    }

    public DashboardStatsDto getAdminStats() {
        DashboardStatsDto stats = new DashboardStatsDto();
        stats.setTotalUsersCount(userRepository.count());
        stats.setTotalJobSeekersCount(userRepository.countByRole(Role.JOB_SEEKER));
        stats.setTotalEmployersCount(userRepository.countByRole(Role.EMPLOYER));
        stats.setTotalJobsInSystem(jobRepository.count());
        stats.setOpenJobsCount(jobRepository.countByStatus(JobStatus.OPEN));
        stats.setPendingJobsCount(jobRepository.countByStatus(JobStatus.PENDING_APPROVAL));
        stats.setTotalApplicationsSystemWide(applicationRepository.count());

        Map<String, Long> appMap = new HashMap<>();
        for (ApplicationStatus status : ApplicationStatus.values()) {
            appMap.put(status.name(), applicationRepository.countByStatus(status));
        }
        stats.setApplicationsByStatus(appMap);

        return stats;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(dtoMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void toggleUserStatus(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    public List<JobDto> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(j -> dtoMapper.toJobDto(j, null, false, false, null))
                .collect(Collectors.toList());
    }

    @Transactional
    public JobDto updateJobStatus(Long jobId, JobStatus status) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        job.setStatus(status);
        job = jobRepository.save(job);
        return dtoMapper.toJobDto(job, null, false, false, null);
    }

    public List<ApplicationDto> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(a -> {
                    JobSeekerProfile profile = seekerProfileRepository.findByUser(a.getCandidate()).orElse(null);
                    return dtoMapper.toApplicationDto(a, profile);
                })
                .collect(Collectors.toList());
    }
}
