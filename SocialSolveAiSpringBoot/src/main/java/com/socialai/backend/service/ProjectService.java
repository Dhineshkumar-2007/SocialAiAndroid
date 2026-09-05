package com.socialai.backend.service;

import com.socialai.backend.model.Milestone;
import com.socialai.backend.model.Project;
import com.socialai.backend.repository.MilestoneRepository;
import com.socialai.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(Long id) {
        return projectRepository.findById(id)
            .orElseGet(() -> projectRepository.save(new Project(id)));
    }

    public List<Milestone> getMilestones(Long projectId) {
        return milestoneRepository.findByProjectIdOrderByIdAsc(projectId);
    }

    public Milestone createMilestone(Long projectId, String title, String description, String dueDate) {
        Milestone milestone = new Milestone(projectId, title, description, dueDate);
        return milestoneRepository.save(milestone);
    }
}
