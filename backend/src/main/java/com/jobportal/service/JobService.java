package com.jobportal.service;

import com.jobportal.dto.JobDto;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobStatus;
import com.jobportal.entity.JobType;
import com.jobportal.exception.ResourceNotFoundException;
import com.jobportal.repository.JobRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobDto> search(String keyword, String location, String jobType) {
        Specification<Job> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), JobStatus.OPEN));
            if (keyword != null && !keyword.isBlank()) {
                String like = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), like),
                        cb.like(cb.lower(root.get("description")), like),
                        cb.like(cb.lower(root.get("skills")), like)
                ));
            }
            if (location != null && !location.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
            }
            if (jobType != null && !jobType.isBlank()) {
                try {
                    predicates.add(cb.equal(root.get("jobType"), JobType.valueOf(jobType)));
                } catch (IllegalArgumentException ignored) {
                    // ignore invalid filter so the search still returns open jobs
                }
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return jobRepository.findAll(spec).stream().map(DtoMapper::toJob).toList();
    }

    public JobDto getById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        return DtoMapper.toJob(job);
    }

    public Job getEntity(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
    }
}
