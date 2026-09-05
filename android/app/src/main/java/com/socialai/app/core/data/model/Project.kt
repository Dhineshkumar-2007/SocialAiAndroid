package com.socialai.app.core.data.model
import kotlinx.serialization.Serializable
@Serializable
data class Project(val id: String, val problemId: String, val problem: Problem? = null, val orgId: String, val teamMembers: List<User> = emptyList(), val milestones: List<Milestone> = emptyList(), val status: String = "IN_PROGRESS", val createdAt: String? = null)
@Serializable
data class Milestone(val id: String, val projectId: String, val title: String, val description: String? = null, val dueDate: String? = null, val status: String = "PENDING", val evidenceUrls: List<String> = emptyList(), val createdAt: String? = null)
