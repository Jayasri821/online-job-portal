package com.careermatch.service;

import com.careermatch.dto.ApplicationStrengthDto;
import com.careermatch.dto.JobMatchResultDto;
import com.careermatch.entity.Job;
import com.careermatch.entity.JobSeekerProfile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationStrengthService {

    private final JobMatchingService jobMatchingService;

    public ApplicationStrengthService(JobMatchingService jobMatchingService) {
        this.jobMatchingService = jobMatchingService;
    }

    public ApplicationStrengthDto evaluateStrength(JobSeekerProfile profile, Job job) {
        JobMatchResultDto match = jobMatchingService.calculateMatch(profile, job);

        ApplicationStrengthDto dto = new ApplicationStrengthDto();
        dto.setJobId(job.getId());
        dto.setJobTitle(job.getTitle());

        List<String> strong = new ArrayList<>();
        List<String> weak = new ArrayList<>();
        List<String> suggestions = new ArrayList<>();

        int score = match.getOverallMatchScore();

        // Evaluate Strong Points
        if (!match.getMatchedSkills().isEmpty()) {
            strong.add("Required technical skills match: " + String.join(", ", match.getMatchedSkills()));
        }
        if (profile != null && profile.getProjects() != null && profile.getProjects().length() > 20) {
            strong.add("Relevant project portfolio present in profile");
        }
        if (profile != null && profile.getDegree() != null) {
            strong.add("Degree/Education (" + profile.getDegree() + ") aligns with requirements");
        }
        if (profile != null && profile.getResumeUrl() != null && !profile.getResumeUrl().isEmpty()) {
            strong.add("Verified resume attached");
        }

        // Evaluate Weak Points & Suggestions
        if (!match.getMissingSkills().isEmpty()) {
            weak.add("Missing required skills: " + String.join(", ", match.getMissingSkills()));
            suggestions.add("Brush up on " + match.getMissingSkills().get(0) + " before attending technical interviews");
        }
        if (profile == null || profile.getResumeUrl() == null || profile.getResumeUrl().isEmpty()) {
            weak.add("No PDF resume uploaded to your candidate profile");
            suggestions.add("Upload a structured PDF resume to stand out to the recruiter");
        }
        if (profile == null || profile.getExperienceYears() < (job.getExperienceYears() != null ? job.getExperienceYears() : 0)) {
            weak.add("Experience is below the requested " + job.getExperienceYears() + " year(s)");
            suggestions.add("Highlight hands-on academic projects or open-source work in your cover letter");
        }

        if (strong.isEmpty()) {
            strong.add("General interest and baseline technical readiness");
        }

        String verdict;
        if (score >= 80) verdict = "Strong Candidate";
        else if (score >= 65) verdict = "Competitive Profile";
        else if (score >= 50) verdict = "Moderate Match - Recommended to Upskill";
        else verdict = "High Skill Gap - Consider Skill Upgrades";

        dto.setStrengthScore(score);
        dto.setVerdict(verdict);
        dto.setStrongPoints(strong);
        dto.setWeakPoints(weak);
        dto.setImprovementSuggestions(suggestions);

        return dto;
    }
}
