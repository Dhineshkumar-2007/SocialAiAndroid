package com.socialai.app.core.data.model
import kotlinx.serialization.Serializable
enum class UserRole { CITIZEN, UNIVERSITY, INDUSTRY, ADMIN, FACULTY, STUDENT }
@Serializable
data class User(val id: String, val name: String, val email: String, val role: String, val orgId: String? = null)
