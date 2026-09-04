package com.careermatch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "interview_questions")
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category; // e.g. "Java", "Spring Boot", "SQL", "HR", "System Design", "Project"

    private String targetRole; // e.g. "Java Developer", "Full Stack Developer", "ALL"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType; // TECHNICAL, HR, PROJECT

    @Column(nullable = false, length = 1000)
    private String question;

    @Column(columnDefinition = "TEXT")
    private String sampleAnswer;

    @Column(columnDefinition = "TEXT")
    private String keyPoints;

    private Integer difficultyLevel = 1; // 1: Beginner, 2: Intermediate, 3: Advanced

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
}
