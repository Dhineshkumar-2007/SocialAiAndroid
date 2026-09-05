package com.socialai.backend.dto;

public class CreateProblemRequest {
    private String title;
    private String description;
    private String district;
    private Double latitude;
    private Double longitude;

    public CreateProblemRequest() {}

    public CreateProblemRequest(String title, String description, String district, Double latitude, Double longitude) {
        this.title = title;
        this.description = description;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
