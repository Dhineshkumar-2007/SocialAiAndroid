package com.socialai.backend.controller;

import com.socialai.backend.dto.DashboardStatsDto;
import com.socialai.backend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getStats() {
        return ResponseEntity.ok(dashboardService.getPublicStats());
    }

    @GetMapping("/admin")
    public ResponseEntity<DashboardStatsDto> getAdminStats() {
        return ResponseEntity.ok(dashboardService.getPublicStats());
    }
}
