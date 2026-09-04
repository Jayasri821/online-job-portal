package com.careermatch.dto;

import com.careermatch.entity.PracticeStatus;
import com.careermatch.entity.QuestionType;

public class InterviewQuestionDto {
    private Long id;
    private String category;
    private String targetRole;
    private QuestionType questionType;
    private String question;
    private String sampleAnswer;
    private String keyPoints;
    private Integer difficultyLevel;
    private PracticeStatus practiceStatus = PracticeStatus.NOT_STARTED;
    private String userNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSampleAnswer() {
        return sampleAnswer;
    }

    public void setSampleAnswer(String sampleAnswer) {
        this.sampleAnswer = sampleAnswer;
    }

    public String getKeyPoints() {
        return keyPoints;
    }

    public void setKeyPoints(String keyPoints) {
        this.keyPoints = keyPoints;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public PracticeStatus getPracticeStatus() {
        return practiceStatus;
    }

    public void setPracticeStatus(PracticeStatus practiceStatus) {
        this.practiceStatus = practiceStatus;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }
}
