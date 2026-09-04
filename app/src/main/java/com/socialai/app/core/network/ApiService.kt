package com.socialai.app.core.network
import com.socialai.app.core.data.model.*
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import retrofit2.http.*
@Serializable data class LoginRequest(val email: String, val password: String)
@Serializable data class RegisterRequest(val name: String, val email: String, val password: String, val role: String, val orgId: String? = null)
@Serializable data class CreateProblemRequest(val title: String, val description: String, val district: String, val latitude: Double? = null, val longitude: Double? = null)
@Serializable data class DeclineRequest(val reason: String)
@Serializable data class CreateMilestoneRequest(val title: String, val description: String? = null, val dueDate: String? = null)
@Serializable data class VerifyRequest(val isResolved: Boolean, val rating: Int)
interface ApiService {
    @POST("auth/login") suspend fun login(@Body request: LoginRequest): AuthResponse
    @POST("auth/register") suspend fun register(@Body request: RegisterRequest): AuthResponse
    @GET("problems") suspend fun getProblems(): List<Problem>
    @GET("problems/mine") suspend fun getMyProblems(): List<Problem>
    @POST("problems") suspend fun createProblem(@Body request: CreateProblemRequest): Problem
    @GET("problems/{id}") suspend fun getProblem(@Path("id") id: String): Problem
    @POST("problems/{id}/analyze") suspend fun analyzeProblem(@Path("id") id: String): AiAnalysisResult
    @POST("problems/{id}/verify") suspend fun verifyProblem(@Path("id") id: String, @Body request: VerifyRequest): MessageResponse
    @GET("assignments/inbox") suspend fun getAssignmentInbox(): List<Assignment>
    @GET("assignments/{id}") suspend fun getAssignment(@Path("id") id: String): Assignment
    @PUT("assignments/{id}/accept") suspend fun acceptAssignment(@Path("id") id: String): Assignment
    @PUT("assignments/{id}/decline") suspend fun declineAssignment(@Path("id") id: String, @Body request: DeclineRequest): Assignment
    @GET("projects") suspend fun getProjects(): List<Project>
    @GET("projects/{id}") suspend fun getProject(@Path("id") id: String): Project
    @POST("projects/{id}/milestones") suspend fun createMilestone(@Path("id") projectId: String, @Body request: CreateMilestoneRequest): Milestone
    @Multipart @POST("milestones/{id}/evidence") suspend fun uploadEvidence(@Path("id") milestoneId: String, @Part file: MultipartBody.Part): Milestone
    @GET("dashboard/stats") suspend fun getDashboardStats(): DashboardStats
    @GET("dashboard/admin") suspend fun getAdminDashboard(): DashboardStats
}
