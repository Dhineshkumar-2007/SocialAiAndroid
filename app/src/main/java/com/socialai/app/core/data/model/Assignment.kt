package com.socialai.app.core.data.model
import kotlinx.serialization.Serializable
@Serializable
data class Assignment(val id: String, val problemId: String, val problem: Problem? = null, val assignedOrgId: String, val status: String = "PENDING", val declineReason: String? = null, val daysRemaining: Int? = null, val createdAt: String? = null)
