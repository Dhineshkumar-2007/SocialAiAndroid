package com.socialai.backend.service;

import com.socialai.backend.dto.CreateProblemRequest;
import com.socialai.backend.dto.CreateProblemResponse;
import com.socialai.backend.model.Problem;
import com.socialai.backend.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;

    public CreateProblemResponse createProblem(CreateProblemRequest request, String createdBy) {
        Problem problem = new Problem(
            request.getTitle(),
            request.getDescription(),
            request.getDistrict() != null ? request.getDistrict() : "General",
            request.getLatitude(),
            request.getLongitude()
        );
        problem.setCreatedBy(createdBy);
        problem = problemRepository.save(problem);

        return new CreateProblemResponse("Problem submitted successfully", problem.getId());
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAllByOrderByIdDesc();
    }

    public List<Problem> getMyProblems(String userId) {
        return problemRepository.findByCreatedByOrderByIdDesc(userId);
    }

    public Problem getProblem(Long id) {
        return problemRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Problem not found with id: " + id));
    }

    public Problem save(Problem problem) {
        return problemRepository.save(problem);
    }
}
