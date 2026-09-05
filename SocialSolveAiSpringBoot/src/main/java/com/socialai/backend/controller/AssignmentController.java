package com.socialai.backend.controller;

import com.socialai.backend.dto.DeclineRequest;
import com.socialai.backend.model.Assignment;
import com.socialai.backend.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/inbox")
    public ResponseEntity<List<Assignment>> getInbox() {
        return ResponseEntity.ok(assignmentService.getInbox("guest_hei"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable Long id) {
        try {
            Assignment assignment = assignmentService.getAssignment(id);
            return ResponseEntity.ok(assignment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<?> acceptAssignment(@PathVariable Long id) {
        try {
            Assignment assignment = assignmentService.accept(id);
            return ResponseEntity.ok(assignment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/decline")
    public ResponseEntity<?> declineAssignment(@PathVariable Long id, @RequestBody DeclineRequest request) {
        try {
            Assignment assignment = assignmentService.decline(id, request.getReason());
            return ResponseEntity.ok(assignment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
