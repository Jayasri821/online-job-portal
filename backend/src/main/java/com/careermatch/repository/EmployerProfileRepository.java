package com.careermatch.repository;

import com.careermatch.entity.EmployerProfile;
import com.careermatch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerProfileRepository extends JpaRepository<EmployerProfile, Long> {
    Optional<EmployerProfile> findByUser(User user);
    Optional<EmployerProfile> findByUserId(Long userId);
    Optional<EmployerProfile> findByCompanyNameIgnoreCase(String companyName);
}
