package com.socialai.backend.service;

import com.socialai.backend.dto.AiAnalysisResultDto;
import com.socialai.backend.model.Problem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiEngineService {

    public AiAnalysisResultDto analyze(Problem problem) {
        String titleDesc = (problem.getTitle() + " " + problem.getDescription()).toLowerCase();

        String category = "Environment";
        if (titleDesc.contains("water") || titleDesc.contains("drain") || titleDesc.contains("leak")) {
            category = "Water and Sanitation";
        } else if (titleDesc.contains("street") || titleDesc.contains("road") || titleDesc.contains("light") || titleDesc.contains("traffic")) {
            category = "Infrastructure";
        } else if (titleDesc.contains("health") || titleDesc.contains("hospital") || titleDesc.contains("doctor")) {
            category = "Healthcare";
        } else if (titleDesc.contains("school") || titleDesc.contains("education") || titleDesc.contains("student")) {
            category = "Education";
        } else if (titleDesc.contains("crop") || titleDesc.contains("farmer") || titleDesc.contains("agriculture")) {
            category = "Agriculture";
        }

        List<String> skills = new ArrayList<>();
        if (category.equals("Water and Sanitation")) {
            skills.add("Environmental Engineering");
            skills.add("Hydrology");
            skills.add("Water Quality Analysis");
        } else if (category.equals("Infrastructure")) {
            skills.add("IoT");
            skills.add("Embedded Systems");
            skills.add("Civil Engineering");
        } else {
            skills.add("Data Science");
            skills.add("Community Development");
            skills.add("Resource Management");
        }

        AiAnalysisResultDto.ClassificationDto classification = new AiAnalysisResultDto.ClassificationDto(category, 0.89);
        AiAnalysisResultDto.PriorityDto priority = new AiAnalysisResultDto.PriorityDto("high", 82.5);

        List<AiAnalysisResultDto.UniversityMatchDto> universities = new ArrayList<>();
        universities.add(new AiAnalysisResultDto.UniversityMatchDto(
            "1",
            "National Institute of Technology Tiruchirappalli (NIT Trichy)",
            89.5,
            0.895,
            List.of("Strong research in " + category, "Dedicated IoT & Environmental Engineering Labs")
        ));
        universities.add(new AiAnalysisResultDto.UniversityMatchDto(
            "2",
            "Indian Institute of Technology Madras (IIT Madras)",
            84.0,
            0.840,
            List.of("Active Smart City research group", "Available project capacity")
        ));

        List<AiAnalysisResultDto.IndustryMatchDto> industries = new ArrayList<>();
        industries.add(new AiAnalysisResultDto.IndustryMatchDto(
            "1",
            "Tamil Nadu Water Supply / Infrastructure Utility",
            92.0,
            0.920,
            List.of("Direct sector match for public utilities", "CSR funding & deployment partner")
        ));
        industries.add(new AiAnalysisResultDto.IndustryMatchDto(
            "2",
            "L&T Construction & Smart Infrastructure",
            78.5,
            0.785,
            List.of("Infrastructure technology partner", "Prototyping capabilities")
        ));

        return new AiAnalysisResultDto(classification, priority, skills, universities, industries);
    }
}
