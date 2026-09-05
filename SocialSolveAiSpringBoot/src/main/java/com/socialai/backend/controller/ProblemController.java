package com.socialai.backend.controller;

import com.socialai.backend.dto.AiAnalysisResultDto;
import com.socialai.backend.dto.CreateProblemRequest;
import com.socialai.backend.dto.CreateProblemResponse;
import com.socialai.backend.dto.VerifyRequest;
import com.socialai.backend.model.Problem;
import com.socialai.backend.model.ResolutionFeedback;
import com.socialai.backend.repository.ResolutionFeedbackRepository;
import com.socialai.backend.service.AiEngineService;
import com.socialai.backend.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private AiEngineService aiEngineService;

    @Autowired
    private ResolutionFeedbackRepository resolutionFeedbackRepository;

    @PostMapping
    public ResponseEntity<CreateProblemResponse> createProblem(@RequestBody CreateProblemRequest request) {
        CreateProblemResponse response = problemService.createProblem(request, "guest");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Problem>> getAllProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    @GetMapping("/mine")
    public ResponseEntity<List<Problem>> getMyProblems() {
        return ResponseEntity.ok(problemService.getAllProblems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProblem(@PathVariable Long id) {
        try {
            Problem problem = problemService.getProblem(id);
            return ResponseEntity.ok(problem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/analyze")
    public ResponseEntity<?> analyzeProblem(@PathVariable Long id) {
        try {
            Problem problem = problemService.getProblem(id);
            AiAnalysisResultDto analysisResult = aiEngineService.analyze(problem);

            problem.setCategory(analysisResult.getClassification().getCategory());
            problem.setPriorityLevel(analysisResult.getPriority().getLevel());
            problem.setPriorityScore(analysisResult.getPriority().getScore());
            problem.setStatus("analyzed");
            problemService.save(problem);

            return ResponseEntity.ok(analysisResult);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<?> verifyProblem(@PathVariable Long id, @RequestBody VerifyRequest request) {
        try {
            Problem problem = problemService.getProblem(id);

            ResolutionFeedback feedback = new ResolutionFeedback(
                id,
                "guest",
                request.getIsResolved(),
                request.getRating()
            );
            feedback.setFeedbackText(request.getFeedbackText());
            resolutionFeedbackRepository.save(feedback);

            if (Boolean.TRUE.equals(request.getIsResolved())) {
                problem.setStatus("completed");
                problemService.save(problem);
            }

            return ResponseEntity.ok(Map.of("message", "Resolution verified successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }
}
