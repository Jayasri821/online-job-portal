package com.careermatch.service;

import com.careermatch.dto.JobMatchResultDto;
import com.careermatch.dto.SkillGapDto;
import com.careermatch.entity.Job;
import com.careermatch.entity.JobSeekerProfile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SkillGapService {

    private final JobMatchingService jobMatchingService;

    public SkillGapService(JobMatchingService jobMatchingService) {
        this.jobMatchingService = jobMatchingService;
    }

    public SkillGapDto analyzeSkillGap(JobSeekerProfile profile, Job job) {
        JobMatchResultDto matchResult = jobMatchingService.calculateMatch(profile, job);

        SkillGapDto dto = new SkillGapDto();
        dto.setJobId(job.getId());
        dto.setJobTitle(job.getTitle());
        dto.setCompanyName(job.getEmployer() != null ? job.getEmployer().getCompanyName() : "Company");

        List<String> userSkills = profile != null ? jobMatchingService.parseSkills(profile.getSkills()) : new ArrayList<>();
        List<String> reqSkills = jobMatchingService.parseSkills(job.getRequiredSkills());

        dto.setCandidateSkills(userSkills);
        dto.setRequiredSkills(reqSkills);
        dto.setMatchedSkills(matchResult.getMatchedSkills());
        dto.setMissingSkills(matchResult.getMissingSkills());

        double ratio = reqSkills.isEmpty() ? 100.0 : ((double) matchResult.getMatchedSkills().size() / reqSkills.size()) * 100.0;
        dto.setSkillMatchPercentage(Math.round(ratio * 10.0) / 10.0);

        List<SkillGapDto.RecommendedSkillDto> recommendations = new ArrayList<>();
        int priorityCounter = 1;
        for (String missing : matchResult.getMissingSkills()) {
            String priority = (priorityCounter <= 2) ? "HIGH" : "MEDIUM";
            String reason = "Required for " + job.getTitle() + " role at " + dto.getCompanyName();
            String resource = getResourceForSkill(missing);

            recommendations.add(new SkillGapDto.RecommendedSkillDto(missing, priority, reason, resource));
            priorityCounter++;
        }

        if (recommendations.isEmpty()) {
            recommendations.add(new SkillGapDto.RecommendedSkillDto(
                    "System Design & Clean Code",
                    "LOW",
                    "You have 100% of required technical skills! Focus on architecture and interview readiness.",
                    "https://github.com/donnemartin/system-design-primer"
            ));
        }

        dto.setRecommendedSkillsToLearn(recommendations);
        return dto;
    }

    private String getResourceForSkill(String skill) {
        String s = skill.toLowerCase();
        if (s.contains("spring")) return "https://spring.io/guides";
        if (s.contains("react")) return "https://react.dev/learn";
        if (s.contains("java")) return "https://dev.java/learn/";
        if (s.contains("docker") || s.contains("devops")) return "https://docs.docker.com/get-started/";
        if (s.contains("aws") || s.contains("cloud")) return "https://aws.amazon.com/training/";
        if (s.contains("sql") || s.contains("database")) return "https://www.w3schools.com/sql/";
        if (s.contains("python")) return "https://docs.python.org/3/tutorial/";
        if (s.contains("node")) return "https://nodejs.org/en/learn";
        return "https://www.geeksforgeeks.org/" + skill.toLowerCase().replaceAll("[^a-z0-9]", "-");
    }
}
