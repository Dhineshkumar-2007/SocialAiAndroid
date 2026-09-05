package com.socialai.backend.repository;

import com.socialai.backend.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByCreatedByOrderByIdDesc(String createdBy);
    List<Problem> findAllByOrderByIdDesc();
    long countByStatus(String status);
}
