package com.socialai.app.core.data.model
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String? = "",
    val user: User
)

@Serializable
data class MessageResponse(val message: String)
