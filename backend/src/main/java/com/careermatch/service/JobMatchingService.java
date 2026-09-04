package com.careermatch.service;

import com.careermatch.dto.JobMatchResultDto;
import com.careermatch.entity.Job;
import com.careermatch.entity.JobSeekerProfile;
import com.careermatch.entity.WorkMode;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobMatchingService {

    public JobMatchResultDto calculateMatch(JobSeekerProfile profile, Job job) {
        JobMatchResultDto result = new JobMatchResultDto();
        result.setJobId(job.getId());
        result.setJobTitle(job.getTitle());
        result.setCompanyName(job.getEmployer() != null ? job.getEmployer().getCompanyName() : "Confidential");

        if (profile == null) {
            result.setOverallMatchScore(50);
            result.setSkillsScore(25.0);
            result.setExperienceScore(10.0);
            result.setEducationScore(5.0);
            result.setLocationScore(5.0);
            result.setSalaryScore(5.0);
            result.setWorkModeScore(0.0);
            result.setMatchedSkills(Collections.emptyList());
            result.setMissingSkills(parseSkills(job.getRequiredSkills()));
            result.setWhyThisJobExplanations(Collections.singletonList("Complete your profile to see detailed match analysis."));
            result.setMatchVerdict("Profile Incomplete");
            return result;
        }

        List<String> jobSkills = parseSkills(job.getRequiredSkills());
        List<String> userSkills = parseSkills(profile.getSkills());

        // 1. Skill Match (Weight: 50%)
        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        for (String reqSkill : jobSkills) {
            boolean matched = userSkills.stream()
                    .anyMatch(uSkill -> isSkillMatch(uSkill, reqSkill));
            if (matched) {
                matchedSkills.add(reqSkill);
            } else {
                missingSkills.add(reqSkill);
            }
        }

        double skillRatio = jobSkills.isEmpty() ? 1.0 : (double) matchedSkills.size() / jobSkills.size();
        double skillsScore = Math.round(skillRatio * 50.0 * 10.0) / 10.0;

        // 2. Experience Match (Weight: 15%)
        int reqExp = job.getExperienceYears() != null ? job.getExperienceYears() : 0;
        int userExp = profile.getExperienceYears() != null ? profile.getExperienceYears() : 0;
        double experienceScore;
        if (reqExp == 0 || userExp >= reqExp) {
            experienceScore = 15.0;
        } else {
            experienceScore = Math.round(((double) userExp / reqExp) * 15.0 * 10.0) / 10.0;
        }

        // 3. Education Match (Weight: 10%)
        double educationScore = 7.0;
        if (profile.getDegree() != null && !profile.getDegree().trim().isEmpty()) {
            educationScore = 10.0;
        } else if (profile.getEducation() != null && !profile.getEducation().trim().isEmpty()) {
            educationScore = 9.0;
        }

        // 4. Location Match (Weight: 10%)
        double locationScore = 5.0;
        if (job.getWorkMode() == WorkMode.WORK_FROM_HOME ||
                (profile.getPreferredWorkMode() != null && profile.getPreferredWorkMode() == WorkMode.WORK_FROM_HOME)) {
            locationScore = 10.0;
        } else if (profile.getPreferredLocation() != null && job.getLocation() != null) {
            String pLoc = profile.getPreferredLocation().trim().toLowerCase();
            String jLoc = job.getLocation().trim().toLowerCase();
            if (pLoc.contains(jLoc) || jLoc.contains(pLoc) || pLoc.contains("any") || pLoc.contains("remote")) {
                locationScore = 10.0;
            } else {
                locationScore = 6.0;
            }
        } else {
            locationScore = 8.0;
        }

        // 5. Salary Match (Weight: 10%)
        double salaryScore = 8.0;
        if (profile.getExpectedSalary() != null && job.getSalaryMax() != null) {
            if (profile.getExpectedSalary() <= job.getSalaryMax()) {
                salaryScore = 10.0;
            } else {
                double diffRatio = (double) job.getSalaryMax() / profile.getExpectedSalary();
                salaryScore = Math.max(4.0, Math.round(diffRatio * 10.0 * 10.0) / 10.0);
            }
        } else {
            salaryScore = 9.0;
        }

        // 6. Work Mode Match (Weight: 5%)
        double workModeScore = 3.0;
        if (profile.getPreferredWorkMode() != null && job.getWorkMode() != null) {
            if (profile.getPreferredWorkMode() == job.getWorkMode()) {
                workModeScore = 5.0;
            } else if (profile.getPreferredWorkMode() == WorkMode.HYBRID || job.getWorkMode() == WorkMode.HYBRID) {
                workModeScore = 4.0;
            } else {
                workModeScore = 2.0;
            }
        } else {
            workModeScore = 4.0;
        }

        // Overall composite score
        double total = skillsScore + experienceScore + educationScore + locationScore + salaryScore + workModeScore;
        int overallScore = (int) Math.min(100, Math.max(10, Math.round(total)));

        // Dynamic Explanations
        List<String> explanations = new ArrayList<>();
        if (!jobSkills.isEmpty()) {
            explanations.add(matchedSkills.size() + " of " + jobSkills.size() + " required skills match your profile");
        }
        if (profile.getPreferredRole() != null && !profile.getPreferredRole().isEmpty()) {
            if (job.getTitle().toLowerCase().contains(profile.getPreferredRole().toLowerCase()) ||
                    profile.getPreferredRole().toLowerCase().contains(job.getTitle().toLowerCase())) {
                explanations.add("Your preferred role (" + profile.getPreferredRole() + ") strongly aligns with this opening");
            }
        }
        if (locationScore >= 9.0) {
            explanations.add("Location / work setup matches your preferences");
        }
        if (salaryScore >= 9.0) {
            explanations.add("Salary package is within or exceeds your expected range");
        }
        if (experienceScore >= 14.0) {
            explanations.add("Your experience level meets the role requirement");
        }
        if (explanations.isEmpty()) {
            explanations.add("Matches several core areas of your candidate profile");
        }

        String verdict;
        if (overallScore >= 85) verdict = "Excellent Match";
        else if (overallScore >= 70) verdict = "Strong Match";
        else if (overallScore >= 55) verdict = "Good Match";
        else verdict = "Moderate Match";

        result.setOverallMatchScore(overallScore);
        result.setSkillsScore(skillsScore);
        result.setExperienceScore(experienceScore);
        result.setEducationScore(educationScore);
        result.setLocationScore(locationScore);
        result.setSalaryScore(salaryScore);
        result.setWorkModeScore(workModeScore);
        result.setMatchedSkills(matchedSkills);
        result.setMissingSkills(missingSkills);
        result.setWhyThisJobExplanations(explanations);
        result.setMatchVerdict(verdict);

        return result;
    }

    public List<String> parseSkills(String rawSkills) {
        if (rawSkills == null || rawSkills.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(rawSkills.split("[,;/|]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private boolean isSkillMatch(String userSkill, String reqSkill) {
        String u = userSkill.trim().toLowerCase();
        String r = reqSkill.trim().toLowerCase();
        if (u.equals(r)) return true;
        if (u.contains(r) || r.contains(u)) return true;

        // Common tech aliases
        if ((u.contains("react") && r.contains("react")) ||
            (u.contains("spring") && r.contains("spring")) ||
            (u.contains("sql") && r.contains("sql")) ||
            (u.contains("js") && r.contains("javascript")) ||
            (u.contains("javascript") && r.contains("js")) ||
            (u.contains("rest") && r.contains("rest")) ||
            (u.contains("aws") && r.contains("cloud")) ||
            (u.contains("docker") && r.contains("devops"))) {
            return true;
        }
        return false;
    }
}
