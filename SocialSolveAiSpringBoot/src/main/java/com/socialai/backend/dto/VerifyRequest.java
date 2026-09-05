package com.socialai.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyRequest {

    @JsonProperty("is_resolved")
    private Boolean isResolved;

    private Integer rating;
    private String feedbackText;

    public VerifyRequest() {}

    public VerifyRequest(Boolean isResolved, Integer rating, String feedbackText) {
        this.isResolved = isResolved;
        this.rating = rating;
        this.feedbackText = feedbackText;
    }

    public Boolean getIsResolved() { return isResolved; }
    public void setIsResolved(Boolean isResolved) { this.isResolved = isResolved; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getFeedbackText() { return feedbackText; }
    public void setFeedbackText(String feedbackText) { this.feedbackText = feedbackText; }
}
