package com.socialai.backend.service;

import com.socialai.backend.model.Assignment;
import com.socialai.backend.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getInbox(String assigneeId) {
        return assignmentRepository.findByAssigneeIdOrderByIdDesc(assigneeId);
    }

    public Assignment getAssignment(Long id) {
        return assignmentRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Assignment not found with id: " + id));
    }

    public Assignment accept(Long id) {
        Assignment assignment = getAssignment(id);
        assignment.setStatus("ACCEPTED");
        return assignmentRepository.save(assignment);
    }

    public Assignment decline(Long id, String reason) {
        Assignment assignment = getAssignment(id);
        assignment.setStatus("DECLINED");
        assignment.setDeclineReason(reason);
        return assignmentRepository.save(assignment);
    }
}
