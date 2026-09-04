package com.jobportal.repository;

import com.jobportal.entity.Interview;
import com.jobportal.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    Optional<Interview> findByApplication(JobApplication application);
}
