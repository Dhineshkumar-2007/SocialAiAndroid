package com.socialai.backend.controller;

import com.socialai.backend.model.Milestone;
import com.socialai.backend.model.Project;
import com.socialai.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @GetMapping("/{id}/milestones")
    public ResponseEntity<List<Milestone>> getMilestones(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getMilestones(id));
    }
}
