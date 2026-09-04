package com.careermatch.service;

import com.careermatch.dto.CareerReadinessDto;
import com.careermatch.entity.JobSeekerProfile;
import com.careermatch.entity.PracticeStatus;
import com.careermatch.entity.User;
import com.careermatch.repository.InterviewProgressRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareerScoreService {

    private final JobMatchingService jobMatchingService;
    private final InterviewProgressRepository interviewProgressRepository;

    public CareerScoreService(JobMatchingService jobMatchingService,
                              InterviewProgressRepository interviewProgressRepository) {
        this.jobMatchingService = jobMatchingService;
        this.interviewProgressRepository = interviewProgressRepository;
    }

    public CareerReadinessDto calculateCareerReadiness(JobSeekerProfile profile) {
        CareerReadinessDto dto = new CareerReadinessDto();

        if (profile == null) {
            dto.setOverallScore(20);
            dto.setScoreRating("Developing");
            dto.setTopSkillGap("Complete your profile");
            dto.setProfileCompletenessScore(5);
            dto.setTechnicalSkillsScore(5);
            dto.setEducationScore(5);
            dto.setProjectsScore(0);
            dto.setCertificationsScore(0);
            dto.setExperienceScore(0);
            dto.setResumeScore(0);
            dto.setInterviewPrepScore(0);
            dto.setSuggestions(List.of(
                    new CareerReadinessDto.SuggestionDto(15, "Complete your primary profile details", "Profile"),
                    new CareerReadinessDto.SuggestionDto(15, "Add technical skills to your profile", "Skills")
            ));
            return dto;
        }

        User user = profile.getUser();

        // 1. Profile Completeness (max 15)
        int profileScore = 0;
        if (user != null && user.getPhone() != null && !user.getPhone().isEmpty()) profileScore += 3;
        if (profile.getCareerObjective() != null && profile.getCareerObjective().length() > 20) profileScore += 4;
        if (profile.getPreferredRole() != null && profile.getPreferredLocation() != null) profileScore += 4;
        if (profile.getEducation() != null && !profile.getEducation().isEmpty()) profileScore += 4;
        profileScore = Math.min(15, profileScore);

        // 2. Technical Skills (max 20)
        List<String> skills = jobMatchingService.parseSkills(profile.getSkills());
        int skillScore;
        if (skills.size() >= 6) skillScore = 20;
        else if (skills.size() >= 4) skillScore = 15;
        else if (skills.size() >= 2) skillScore = 10;
        else if (skills.size() == 1) skillScore = 5;
        else skillScore = 0;

        // 3. Education (max 10)
        int eduScore = 0;
        if (profile.getDegree() != null && !profile.getDegree().isEmpty()) eduScore += 6;
        if (profile.getGraduationYear() != null && profile.getGraduationYear() > 2000) eduScore += 4;
        eduScore = Math.min(10, eduScore);

        // 4. Projects (max 15)
        int projectScore = 0;
        if (profile.getProjects() != null && profile.getProjects().trim().length() > 10) {
            projectScore = profile.getProjects().length() > 50 ? 15 : 10;
        }

        // 5. Certifications (max 10)
        int certScore = 0;
        if (profile.getCertifications() != null && profile.getCertifications().trim().length() > 10) {
            certScore = 10;
        }

        // 6. Experience / Internships (max 10)
        int expScore = 0;
        if (profile.getExperienceYears() != null && profile.getExperienceYears() > 0) {
            expScore = 10;
        } else if (profile.getInternships() != null && profile.getInternships().trim().length() > 10) {
            expScore = 8;
        }

        // 7. Resume (max 10)
        int resumeScore = 0;
        if (profile.getResumeUrl() != null && !profile.getResumeUrl().isEmpty()) {
            resumeScore = 10;
        }

        // 8. Interview Prep (max 10)
        int interviewScore = 0;
        if (user != null) {
            long completedCount = interviewProgressRepository.countByCandidateAndStatus(user, PracticeStatus.COMPLETED);
            long practicingCount = interviewProgressRepository.countByCandidateAndStatus(user, PracticeStatus.PRACTICING);
            long totalPrep = completedCount * 2 + practicingCount;
            interviewScore = (int) Math.min(10, totalPrep);
        }

        int overall = profileScore + skillScore + eduScore + projectScore + certScore + expScore + resumeScore + interviewScore;
        overall = Math.min(100, Math.max(10, overall));

        // Suggestions
        List<CareerReadinessDto.SuggestionDto> suggestions = new ArrayList<>();
        if (resumeScore < 10) {
            suggestions.add(new CareerReadinessDto.SuggestionDto(10, "Upload your PDF resume to unlock full matching", "Resume"));
        }
        if (projectScore < 15) {
            suggestions.add(new CareerReadinessDto.SuggestionDto(15 - projectScore, "Add 2 key projects with technologies used", "Projects"));
        }
        if (skillScore < 20) {
            suggestions.add(new CareerReadinessDto.SuggestionDto(20 - skillScore, "Add more technical skills (target at least 6 core skills)", "Skills"));
        }
        if (interviewScore < 10) {
            suggestions.add(new CareerReadinessDto.SuggestionDto(10 - interviewScore, "Practice questions in the Interview Preparation module", "Interview"));
        }
        if (certScore < 10) {
            suggestions.add(new CareerReadinessDto.SuggestionDto(10, "Add recognized certifications (AWS, Java, Oracle, Coursera)", "Certifications"));
        }
        if (profileScore < 15) {
            suggestions.add(new CareerReadinessDto.SuggestionDto(15 - profileScore, "Complete your career objective and contact info", "Profile"));
        }

        String topSkillGap = determineTopSkillGap(skills);

        String rating;
        if (overall >= 85) rating = "Industry Ready";
        else if (overall >= 70) rating = "Nearly Ready";
        else if (overall >= 50) rating = "Intermediate";
        else rating = "Developing";

        dto.setOverallScore(overall);
        dto.setScoreRating(rating);
        dto.setTopSkillGap(topSkillGap);
        dto.setProfileCompletenessScore(profileScore);
        dto.setTechnicalSkillsScore(skillScore);
        dto.setEducationScore(eduScore);
        dto.setProjectsScore(projectScore);
        dto.setCertificationsScore(certScore);
        dto.setExperienceScore(expScore);
        dto.setResumeScore(resumeScore);
        dto.setInterviewPrepScore(interviewScore);
        dto.setSuggestions(suggestions);

        return dto;
    }

    private String determineTopSkillGap(List<String> userSkills) {
        List<String> benchmark = List.of("Spring Boot", "Docker", "REST API", "SQL", "React", "AWS", "Git", "Microservices");
        for (String b : benchmark) {
            boolean hasIt = userSkills.stream().anyMatch(s -> s.equalsIgnoreCase(b));
            if (!hasIt) {
                return b;
            }
        }
        return "Microservices Architecture";
    }
}
