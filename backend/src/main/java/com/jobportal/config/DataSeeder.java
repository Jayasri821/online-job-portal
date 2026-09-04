package com.jobportal.config;

import com.jobportal.entity.*;
import com.jobportal.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JobSeekerProfileRepository profileRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            UserRepository userRepository,
            JobSeekerProfileRepository profileRepository,
            CompanyRepository companyRepository,
            JobRepository jobRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        saveUser("Portal Admin", "admin@jobportal.com", "Admin@123", Role.ADMIN);
        User recruiter = saveUser("Priya Sharma", "recruiter@techcorp.com", "Recruiter@123", Role.RECRUITER);
        User seeker = saveUser("Rahul Verma", "seeker@gmail.com", "Seeker@123", Role.JOB_SEEKER);

        JobSeekerProfile profile = new JobSeekerProfile();
        profile.setUser(seeker);
        profile.setPhone("9876543210");
        profile.setLocation("Bengaluru");
        profile.setEducation("B.E. Computer Science");
        profile.setExperience("2 years");
        profile.setSkills("Java, Spring Boot, React, MySQL");
        profile.setSummary("Java developer looking for full-stack roles.");
        profileRepository.save(profile);

        Company company = new Company();
        company.setRecruiter(recruiter);
        company.setName("TechCorp Solutions");
        company.setWebsite("https://techcorp.example.com");
        company.setLocation("Bengaluru");
        company.setIndustry("Information Technology");
        company.setDescription("Product company building HR and hiring tools.");
        companyRepository.save(company);

        saveJob(company, "Java Backend Developer", "Work on Spring Boot REST APIs, JPA and MySQL.",
                "Bengaluru", "Java, Spring Boot, MySQL", 2, 800000, 1400000, JobType.FULL_TIME);
        saveJob(company, "React Frontend Developer", "Build responsive dashboards with React and Axios.",
                "Hyderabad", "React, JavaScript, CSS", 1, 600000, 1100000, JobType.FULL_TIME);
        saveJob(company, "Full Stack Intern", "Learn Java 17, Spring Security and React on real features.",
                "Remote", "Java, React, Git", 0, 15000, 25000, JobType.INTERNSHIP);
    }

    private User saveUser(String name, String email, String rawPassword, Role role) {
        User user = new User();
        user.setFullName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(role);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    private void saveJob(
            Company company,
            String title,
            String description,
            String location,
            String skills,
            int experience,
            int salaryMin,
            int salaryMax,
            JobType type
    ) {
        Job job = new Job();
        job.setCompany(company);
        job.setTitle(title);
        job.setDescription(description);
        job.setLocation(location);
        job.setSkills(skills);
        job.setExperienceYears(experience);
        job.setSalaryMin(salaryMin);
        job.setSalaryMax(salaryMax);
        job.setJobType(type);
        job.setStatus(JobStatus.OPEN);
        jobRepository.save(job);
    }
}
