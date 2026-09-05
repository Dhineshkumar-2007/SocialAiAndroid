package com.socialai.backend.dto;

public class DeclineRequest {
    private String reason;

    public DeclineRequest() {}

    public DeclineRequest(String reason) {
        this.reason = reason;
    }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
