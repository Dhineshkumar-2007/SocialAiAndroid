package com.socialai.backend.dto;

public class DashboardStatsDto {
    private long totalProblems;
    private long resolvedProblems;
    private long inProgressProblems;
    private double resolutionRatePercent;

    public DashboardStatsDto() {}

    public DashboardStatsDto(long totalProblems, long resolvedProblems, long inProgressProblems, double resolutionRatePercent) {
        this.totalProblems = totalProblems;
        this.resolvedProblems = resolvedProblems;
        this.inProgressProblems = inProgressProblems;
        this.resolutionRatePercent = resolutionRatePercent;
    }

    public long getTotalProblems() { return totalProblems; }
    public void setTotalProblems(long totalProblems) { this.totalProblems = totalProblems; }

    public long getResolvedProblems() { return resolvedProblems; }
    public void setResolvedProblems(long resolvedProblems) { this.resolvedProblems = resolvedProblems; }

    public long getInProgressProblems() { return inProgressProblems; }
    public void setInProgressProblems(long inProgressProblems) { this.inProgressProblems = inProgressProblems; }

    public double getResolutionRatePercent() { return resolutionRatePercent; }
    public void setResolutionRatePercent(double resolutionRatePercent) { this.resolutionRatePercent = resolutionRatePercent; }
}
