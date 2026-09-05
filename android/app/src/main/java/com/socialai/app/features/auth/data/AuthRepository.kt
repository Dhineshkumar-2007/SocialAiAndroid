package com.socialai.app.features.auth.data

import com.socialai.app.core.data.model.AuthResponse
import com.socialai.app.core.datastore.SessionManager
import com.socialai.app.core.network.ApiService
import com.socialai.app.core.network.LoginRequest
import com.socialai.app.core.network.RegisterRequest
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        role: String,
        phone: String? = null,
        location: String? = null,
        website: String? = null,
        department: String? = null,
        jurisdiction: String? = null,
        orgName: String? = null,
        orgId: String? = null,
        expertise: String? = null,
        capacity: String? = null
    ): Result<AuthResponse>
    suspend fun continueAsCitizen()
}

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val session: SessionManager
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<AuthResponse> = runCatching {
        val res = api.login(LoginRequest(email, password))
        if (!res.token.isNullOrEmpty()) {
            session.saveSession(res.token, res.user)
        }
        res
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        role: String,
        phone: String?,
        location: String?,
        website: String?,
        department: String?,
        jurisdiction: String?,
        orgName: String?,
        orgId: String?,
        expertise: String?,
        capacity: String?
    ): Result<AuthResponse> = runCatching {
        val res = api.register(
            RegisterRequest(
                name = name,
                email = email,
                password = password,
                role = role.lowercase(),
                phone = phone,
                location = location,
                website = website,
                department = department,
                jurisdiction = jurisdiction,
                orgName = orgName,
                orgId = orgId,
                expertise = expertise,
                capacity = capacity
            )
        )
        if (!res.token.isNullOrEmpty()) {
            session.saveSession(res.token, res.user)
        }
        res
    }

    override suspend fun continueAsCitizen() {
        session.continueAsGuestCitizen()
    }
}
