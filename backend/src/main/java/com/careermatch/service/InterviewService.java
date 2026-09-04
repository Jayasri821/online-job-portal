package com.careermatch.service;

import com.careermatch.dto.InterviewQuestionDto;
import com.careermatch.dto.QuestionProgressRequest;
import com.careermatch.entity.*;
import com.careermatch.exception.ResourceNotFoundException;
import com.careermatch.repository.InterviewProgressRepository;
import com.careermatch.repository.InterviewQuestionRepository;
import com.careermatch.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InterviewService {

    private final InterviewQuestionRepository questionRepository;
    private final InterviewProgressRepository progressRepository;
    private final JobRepository jobRepository;
    private final DtoMapper dtoMapper;

    public InterviewService(InterviewQuestionRepository questionRepository,
                            InterviewProgressRepository progressRepository,
                            JobRepository jobRepository,
                            DtoMapper dtoMapper) {
        this.questionRepository = questionRepository;
        this.progressRepository = progressRepository;
        this.jobRepository = jobRepository;
        this.dtoMapper = dtoMapper;
    }

    public Map<String, Object> getInterviewKitForJob(Long jobId, User user) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        String role = job.getTitle();
        List<InterviewQuestion> allQuestions = questionRepository.findQuestionsForRole(role);
        if (allQuestions.isEmpty()) {
            allQuestions = questionRepository.findAll();
        }

        Map<Long, InterviewProgress> userProgressMap = new HashMap<>();
        if (user != null) {
            List<InterviewProgress> progressList = progressRepository.findByCandidate(user);
            for (InterviewProgress p : progressList) {
                userProgressMap.put(p.getQuestion().getId(), p);
            }
        }

        List<InterviewQuestionDto> techQuestions = new ArrayList<>();
        List<InterviewQuestionDto> hrQuestions = new ArrayList<>();
        List<InterviewQuestionDto> projectQuestions = new ArrayList<>();

        int completedCount = 0;

        for (InterviewQuestion q : allQuestions) {
            InterviewProgress prog = userProgressMap.get(q.getId());
            InterviewQuestionDto dto = dtoMapper.toQuestionDto(q, prog);

            if (dto.getPracticeStatus() == PracticeStatus.COMPLETED) {
                completedCount++;
            }

            if (q.getQuestionType() == QuestionType.TECHNICAL) {
                techQuestions.add(dto);
            } else if (q.getQuestionType() == QuestionType.HR) {
                hrQuestions.add(dto);
            } else if (q.getQuestionType() == QuestionType.PROJECT) {
                projectQuestions.add(dto);
            }
        }

        int totalQuestions = allQuestions.size();
        int progressPercentage = totalQuestions > 0 ? (int) Math.round(((double) completedCount / totalQuestions) * 100) : 0;

        Map<String, Object> response = new HashMap<>();
        response.put("jobId", job.getId());
        response.put("jobTitle", job.getTitle());
        response.put("companyName", job.getEmployer() != null ? job.getEmployer().getCompanyName() : "Company");
        response.put("technicalQuestions", techQuestions);
        response.put("hrQuestions", hrQuestions);
        response.put("projectQuestions", projectQuestions);
        response.put("totalQuestions", totalQuestions);
        response.put("completedQuestions", completedCount);
        response.put("progressPercentage", progressPercentage);

        return response;
    }

    @Transactional
    public InterviewQuestionDto updateQuestionProgress(User user, QuestionProgressRequest request) {
        InterviewQuestion question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + request.getQuestionId()));

        InterviewProgress progress = progressRepository.findByCandidateAndQuestion(user, question)
                .orElseGet(() -> {
                    InterviewProgress p = new InterviewProgress();
                    p.setCandidate(user);
                    p.setQuestion(question);
                    return p;
                });

        progress.setStatus(request.getStatus());
        if (request.getNotes() != null) {
            progress.setNotes(request.getNotes());
        }

        progress = progressRepository.save(progress);
        return dtoMapper.toQuestionDto(question, progress);
    }
}
