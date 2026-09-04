package com.careermatch.repository;

import com.careermatch.entity.ApplicationStatus;
import com.careermatch.entity.Job;
import com.careermatch.entity.JobApplication;
import com.careermatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByCandidateOrderByAppliedAtDesc(User candidate);
    List<JobApplication> findByCandidateIdOrderByAppliedAtDesc(Long candidateId);
    List<JobApplication> findByJobOrderByAppliedAtDesc(Job job);
    List<JobApplication> findByJobIdOrderByAppliedAtDesc(Long jobId);
    List<JobApplication> findByJobEmployerIdOrderByAppliedAtDesc(Long employerId);

    Optional<JobApplication> findByCandidateAndJob(User candidate, Job job);
    boolean existsByCandidateAndJob(User candidate, Job job);

    long countByStatus(ApplicationStatus status);
    long countByJobEmployerId(Long employerId);
    long countByCandidateId(Long candidateId);
}
