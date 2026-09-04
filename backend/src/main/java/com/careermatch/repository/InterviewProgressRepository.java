package com.careermatch.repository;

import com.careermatch.entity.InterviewProgress;
import com.careermatch.entity.InterviewQuestion;
import com.careermatch.entity.PracticeStatus;
import com.careermatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewProgressRepository extends JpaRepository<InterviewProgress, Long> {
    List<InterviewProgress> findByCandidate(User candidate);
    List<InterviewProgress> findByCandidateId(Long candidateId);
    Optional<InterviewProgress> findByCandidateAndQuestion(User candidate, InterviewQuestion question);
    Optional<InterviewProgress> findByCandidateIdAndQuestionId(Long candidateId, Long questionId);
    long countByCandidateAndStatus(User candidate, PracticeStatus status);
}
