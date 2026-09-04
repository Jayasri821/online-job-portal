package com.careermatch.repository;

import com.careermatch.entity.Job;
import com.careermatch.entity.SavedJob;
import com.careermatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByCandidateOrderBySavedAtDesc(User candidate);
    List<SavedJob> findByCandidateIdOrderBySavedAtDesc(Long candidateId);
    Optional<SavedJob> findByCandidateAndJob(User candidate, Job job);
    Optional<SavedJob> findByCandidateIdAndJobId(Long candidateId, Long jobId);
    boolean existsByCandidateAndJob(User candidate, Job job);
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
    void deleteByCandidateAndJob(User candidate, Job job);
}
