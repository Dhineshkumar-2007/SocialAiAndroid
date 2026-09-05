package com.socialai.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateProblemResponse {
    private String message;

    @JsonProperty("problem_id")
    private Long problemId;

    public CreateProblemResponse() {}

    public CreateProblemResponse(String message, Long problemId) {
        this.message = message;
        this.problemId = problemId;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getProblemId() { return problemId; }
    public void setProblemId(Long problemId) { this.problemId = problemId; }
}
