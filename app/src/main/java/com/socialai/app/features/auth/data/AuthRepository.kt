package com.socialai.app.features.auth.data
import com.socialai.app.core.data.model.AuthResponse
import com.socialai.app.core.datastore.SessionManager
import com.socialai.app.core.network.ApiService
import com.socialai.app.core.network.LoginRequest
import com.socialai.app.core.network.RegisterRequest
import javax.inject.Inject
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun register(name: String, email: String, password: String, role: String, orgId: String?): Result<AuthResponse>
    suspend fun continueAsCitizen()
}
class AuthRepositoryImpl @Inject constructor(private val api: ApiService, private val session: SessionManager) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<AuthResponse> = runCatching {
        val res = api.login(LoginRequest(email, password))
        session.saveSession(res.token, res.user)
        res
    }
    override suspend fun register(name: String, email: String, password: String, role: String, orgId: String?): Result<AuthResponse> = runCatching {
        val res = api.register(RegisterRequest(name, email, password, role, orgId))
        session.saveSession(res.token, res.user)
        res
    }
    override suspend fun continueAsCitizen() {
        session.continueAsGuestCitizen()
    }
}
