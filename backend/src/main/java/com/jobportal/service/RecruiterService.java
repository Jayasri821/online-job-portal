package com.jobportal.service;

import com.jobportal.dto.CompanyDto;
import com.jobportal.dto.CompanyRequest;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.JobRequest;
import com.jobportal.entity.Company;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobStatus;
import com.jobportal.entity.JobType;
import com.jobportal.entity.User;
import com.jobportal.exception.ApiException;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.CompanyRepository;
import com.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecruiterService {

    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final CurrentUserService currentUserService;

    public RecruiterService(
            CompanyRepository companyRepository,
            JobRepository jobRepository,
            CurrentUserService currentUserService
    ) {
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.currentUserService = currentUserService;
    }

    public CompanyDto getMyCompany() {
        return companyRepository.findByRecruiter(currentUserService.requireUser())
                .map(DtoMapper::toCompany)
                .orElse(null);
    }

    @Transactional
    public CompanyDto saveCompany(CompanyRequest request) {
        User recruiter = currentUserService.requireUser();
        Company company = companyRepository.findByRecruiter(recruiter).orElseGet(Company::new);
        company.setRecruiter(recruiter);
        company.setName(request.name());
        company.setWebsite(request.website());
        company.setLocation(request.location());
        company.setIndustry(request.industry());
        company.setDescription(request.description());
        return DtoMapper.toCompany(companyRepository.save(company));
    }

    public List<JobDto> getMyJobs() {
        Company company = requireCompany();
        return jobRepository.findByCompanyOrderByPostedAtDesc(company).stream()
                .map(DtoMapper::toJob)
                .toList();
    }

    @Transactional
    public JobDto createJob(JobRequest request) {
        Company company = requireCompany();
        Job job = new Job();
        job.setCompany(company);
        apply(job, request);
        job.setStatus(JobStatus.OPEN);
        return DtoMapper.toJob(jobRepository.save(job));
    }

    @Transactional
    public JobDto updateJob(Long jobId, JobRequest request) {
        Job job = requireOwnedJob(jobId);
        apply(job, request);
        return DtoMapper.toJob(jobRepository.save(job));
    }

    @Transactional
    public JobDto closeJob(Long jobId) {
        Job job = requireOwnedJob(jobId);
        job.setStatus(JobStatus.CLOSED);
        return DtoMapper.toJob(jobRepository.save(job));
    }

    public Company requireCompany() {
        return companyRepository.findByRecruiter(currentUserService.requireUser())
                .orElseThrow(() -> new ApiException("Create your company profile first", 400));
    }

    public Job requireOwnedJob(Long jobId) {
        Company company = requireCompany();
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        if (!job.getCompany().getId().equals(company.getId())) {
            throw new ApiException("You can only manage your own jobs", 403);
        }
        return job;
    }

    private void apply(Job job, JobRequest request) {
        job.setTitle(request.title());
        job.setDescription(request.description());
        job.setLocation(request.location());
        job.setSkills(request.skills());
        job.setExperienceYears(request.experienceYears());
        job.setSalaryMin(request.salaryMin());
        job.setSalaryMax(request.salaryMax());
        job.setJobType(request.jobType() == null ? JobType.FULL_TIME : request.jobType());
    }
}
