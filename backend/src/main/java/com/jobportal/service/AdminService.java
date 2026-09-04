package com.jobportal.service;

import com.jobportal.dto.ApplicationDto;
import com.jobportal.dto.CompanyDto;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.StatsDto;
import com.jobportal.dto.UserDto;
import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobStatus;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.exception.ApiException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final JobApplicationRepository applicationRepository;

    public AdminService(
            UserRepository userRepository,
            CompanyRepository companyRepository,
            JobRepository jobRepository,
            JobApplicationRepository applicationRepository
    ) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
    }

    public List<UserDto> users() {
        return userRepository.findAll().stream().map(DtoMapper::toUser).toList();
    }

    @Transactional
    public UserDto toggleUser(Long userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getRole() == Role.ADMIN) {
            throw new ApiException("Admin account cannot be disabled", 400);
        }
        user.setEnabled(enabled);
        return DtoMapper.toUser(userRepository.save(user));
    }

    public List<CompanyDto> companies() {
        return companyRepository.findAll().stream().map(DtoMapper::toCompany).toList();
    }

    public List<JobDto> jobs() {
        return jobRepository.findAll().stream().map(DtoMapper::toJob).toList();
    }

    @Transactional
    public JobDto updateJobStatus(Long jobId, JobStatus status) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        job.setStatus(status);
        return DtoMapper.toJob(jobRepository.save(job));
    }

    public List<ApplicationDto> applications() {
        return applicationRepository.findAll().stream().map(DtoMapper::toApplication).toList();
    }

    public StatsDto stats() {
        return new StatsDto(
                userRepository.count(),
                userRepository.countByRole(Role.JOB_SEEKER),
                userRepository.countByRole(Role.RECRUITER),
                companyRepository.count(),
                jobRepository.countByStatus(JobStatus.OPEN),
                jobRepository.count(),
                applicationRepository.count(),
                applicationRepository.countByStatus(ApplicationStatus.SHORTLISTED),
                applicationRepository.countByStatus(ApplicationStatus.INTERVIEW_SCHEDULED),
                applicationRepository.countByStatus(ApplicationStatus.REJECTED)
        );
    }
}
