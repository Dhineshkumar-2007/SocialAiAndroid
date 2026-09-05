package com.socialai.app.core.data.model
import kotlinx.serialization.Serializable
@Serializable
data class DashboardStats(val totalProblems: Int = 0, val resolvedProblems: Int = 0, val pendingProblems: Int = 0, val inProgressProblems: Int = 0, val problemsByCategory: Map<String, Int> = emptyMap(), val problemsByDistrict: Map<String, Int> = emptyMap(), val resolutionRatePercent: Double = 0.0, val avgResolutionDays: Double = 0.0, val modelAccuracyHistory: List<AccuracyPoint> = emptyList())
@Serializable
data class AccuracyPoint(val date: String, val accuracy: Double)
