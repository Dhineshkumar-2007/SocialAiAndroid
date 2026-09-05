package com.socialai.backend.repository;

import com.socialai.backend.model.ResolutionFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolutionFeedbackRepository extends JpaRepository<ResolutionFeedback, Long> {
}
