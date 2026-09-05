package com.socialai.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AiAnalysisResultDto {

    private ClassificationDto classification;
    private PriorityDto priority;
    private List<String> skills;
    private List<UniversityMatchDto> matches;

    @JsonProperty("industry_matches")
    private List<IndustryMatchDto> industryMatches;

    public AiAnalysisResultDto() {}

    public AiAnalysisResultDto(ClassificationDto classification, PriorityDto priority, List<String> skills,
                               List<UniversityMatchDto> matches, List<IndustryMatchDto> industryMatches) {
        this.classification = classification;
        this.priority = priority;
        this.skills = skills;
        this.matches = matches;
        this.industryMatches = industryMatches;
    }

    public ClassificationDto getClassification() { return classification; }
    public void setClassification(ClassificationDto classification) { this.classification = classification; }

    public PriorityDto getPriority() { return priority; }
    public void setPriority(PriorityDto priority) { this.priority = priority; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<UniversityMatchDto> getMatches() { return matches; }
    public void setMatches(List<UniversityMatchDto> matches) { this.matches = matches; }

    public List<IndustryMatchDto> getIndustryMatches() { return industryMatches; }
    public void setIndustryMatches(List<IndustryMatchDto> industryMatches) { this.industryMatches = industryMatches; }

    public static class ClassificationDto {
        private String category;
        private Double confidence;

        public ClassificationDto() {}
        public ClassificationDto(String category, Double confidence) {
            this.category = category;
            this.confidence = confidence;
        }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Double getConfidence() { return confidence; }
        public void setConfidence(Double confidence) { this.confidence = confidence; }
    }

    public static class PriorityDto {
        private String level;
        private Double score;

        public PriorityDto() {}
        public PriorityDto(String level, Double score) {
            this.level = level;
            this.score = score;
        }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public Double getScore() { return score; }
        public void setScore(Double score) { this.score = score; }
    }

    public static class UniversityMatchDto {
        @JsonProperty("university_id")
        private String universityId;
        private String university;

        @JsonProperty("match_percent")
        private Double matchPercent;

        @JsonProperty("final_score")
        private Double finalScore;

        private List<String> explanation;

        public UniversityMatchDto() {}
        public UniversityMatchDto(String universityId, String university, Double matchPercent, Double finalScore, List<String> explanation) {
            this.universityId = universityId;
            this.university = university;
            this.matchPercent = matchPercent;
            this.finalScore = finalScore;
            this.explanation = explanation;
        }

        public String getUniversityId() { return universityId; }
        public void setUniversityId(String universityId) { this.universityId = universityId; }

        public String getUniversity() { return university; }
        public void setUniversity(String university) { this.university = university; }

        public Double getMatchPercent() { return matchPercent; }
        public void setMatchPercent(Double matchPercent) { this.matchPercent = matchPercent; }

        public Double getFinalScore() { return finalScore; }
        public void setFinalScore(Double finalScore) { this.finalScore = finalScore; }

        public List<String> getExplanation() { return explanation; }
        public void setExplanation(List<String> explanation) { this.explanation = explanation; }
    }

    public static class IndustryMatchDto {
        @JsonProperty("industry_id")
        private String industryId;
        private String industry;

        @JsonProperty("match_percent")
        private Double matchPercent;

        @JsonProperty("final_score")
        private Double finalScore;

        private List<String> explanation;

        public IndustryMatchDto() {}
        public IndustryMatchDto(String industryId, String industry, Double matchPercent, Double finalScore, List<String> explanation) {
            this.industryId = industryId;
            this.industry = industry;
            this.matchPercent = matchPercent;
            this.finalScore = finalScore;
            this.explanation = explanation;
        }

        public String getIndustryId() { return industryId; }
        public void setIndustryId(String industryId) { this.industryId = industryId; }

        public String getIndustry() { return industry; }
        public void setIndustry(String industry) { this.industry = industry; }

        public Double getMatchPercent() { return matchPercent; }
        public void setMatchPercent(Double matchPercent) { this.matchPercent = matchPercent; }

        public Double getFinalScore() { return finalScore; }
        public void setFinalScore(Double finalScore) { this.finalScore = finalScore; }

        public List<String> getExplanation() { return explanation; }
        public void setExplanation(List<String> explanation) { this.explanation = explanation; }
    }
}
