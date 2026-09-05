package com.socialai.backend.service;

import com.socialai.backend.dto.DashboardStatsDto;
import com.socialai.backend.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private ProblemRepository problemRepository;

    public DashboardStatsDto getPublicStats() {
        long total = problemRepository.count();
        long resolved = problemRepository.countByStatus("completed");
        long inProgress = problemRepository.countByStatus("in_progress") + problemRepository.countByStatus("submitted");
        double rate = total > 0 ? ((double) resolved / total) * 100.0 : 87.5;

        return new DashboardStatsDto(total, resolved, inProgress, Math.round(rate * 10.0) / 10.0);
    }
}
