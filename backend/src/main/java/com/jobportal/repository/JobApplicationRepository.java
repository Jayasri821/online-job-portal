package com.jobportal.repository;

import com.jobportal.entity.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findBySeekerOrderByAppliedAtDesc(User seeker);
    List<JobApplication> findByJobOrderByAppliedAtDesc(Job job);
    Optional<JobApplication> findByJobAndSeeker(Job job, User seeker);
    boolean existsByJobAndSeeker(Job job, User seeker);
    long countByStatus(ApplicationStatus status);
}
