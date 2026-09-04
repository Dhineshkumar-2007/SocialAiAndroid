package com.socialai.app.core.data.model
import kotlinx.serialization.Serializable
@Serializable
data class Problem(val id: String, val title: String, val description: String, val district: String, val latitude: Double? = null, val longitude: Double? = null, val status: String = "PENDING", val priority: String? = null, val photoUrls: List<String> = emptyList(), val reportedBy: String? = null, val createdAt: String? = null, val aiResult: AiAnalysisResult? = null)
