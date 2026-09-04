package com.careermatch.repository;

import com.careermatch.entity.InterviewQuestion;
import com.careermatch.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {
    List<InterviewQuestion> findByQuestionType(QuestionType questionType);
    List<InterviewQuestion> findByCategoryIgnoreCase(String category);

    @Query("SELECT q FROM InterviewQuestion q WHERE LOWER(q.targetRole) = 'all' OR LOWER(q.targetRole) LIKE LOWER(CONCAT('%', :role, '%')) OR LOWER(:role) LIKE LOWER(CONCAT('%', q.category, '%'))")
    List<InterviewQuestion> findQuestionsForRole(@Param("role") String role);
}
