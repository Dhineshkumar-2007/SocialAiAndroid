package com.socialai.app.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class UserRole { PERSON, CITIZEN, INSTITUTION, UNIVERSITY, INDUSTRY, GOVERNMENT, ADMIN, FACULTY, STUDENT }

@Serializable
data class User(
    @Serializable(with = FlexibleStringSerializer::class) val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "citizen",
    val phone: String? = null,
    val location: String? = null,
    val website: String? = null,
    val department: String? = null,
    val jurisdiction: String? = null,
    @SerialName("org_name") val orgName: String? = null,
    @SerialName("org_id") val orgId: String? = null,
    val expertise: List<String> = emptyList(),
    val capacity: String? = null
)

