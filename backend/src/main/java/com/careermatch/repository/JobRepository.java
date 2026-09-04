package com.careermatch.repository;

import com.careermatch.entity.EmployerProfile;
import com.careermatch.entity.Job;
import com.careermatch.entity.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByStatus(JobStatus status);
    List<Job> findByEmployer(EmployerProfile employer);
    List<Job> findByEmployerId(Long employerId);

    @Query("SELECT j FROM Job j WHERE j.status = 'OPEN' AND (" +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(j.requiredSkills) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(j.location) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(j.employer.companyName) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Job> searchOpenJobs(@Param("query") String query);

    long countByStatus(JobStatus status);
}
