package com.careermatch.dto;

import com.careermatch.entity.PracticeStatus;
import jakarta.validation.constraints.NotNull;

public class QuestionProgressRequest {

    @NotNull(message = "Question ID is required")
    private Long questionId;

    @NotNull(message = "Practice status is required")
    private PracticeStatus status;

    private String notes;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public PracticeStatus getStatus() {
        return status;
    }

    public void setStatus(PracticeStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
