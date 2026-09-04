package com.socialai.app.features.problems.data
import com.socialai.app.core.data.model.*
import com.socialai.app.core.network.ApiService
import com.socialai.app.core.network.CreateProblemRequest
import com.socialai.app.core.network.VerifyRequest
import javax.inject.Inject
interface ProblemRepository {
    suspend fun getAllProblems(): Result<List<Problem>>
    suspend fun getMyProblems(): Result<List<Problem>>
    suspend fun getProblem(id: String): Result<Problem>
    suspend fun createProblem(request: CreateProblemRequest): Result<Problem>
    suspend fun analyzeProblem(id: String): Result<AiAnalysisResult>
    suspend fun verifyResolution(id: String, isResolved: Boolean, rating: Int): Result<Unit>
}
class ProblemRepositoryImpl @Inject constructor(private val api: ApiService) : ProblemRepository {
    override suspend fun getAllProblems() = runCatching { api.getProblems() }
    override suspend fun getMyProblems() = runCatching { api.getMyProblems() }
    override suspend fun getProblem(id: String) = runCatching { api.getProblem(id) }
    override suspend fun createProblem(request: CreateProblemRequest) = runCatching { api.createProblem(request) }
    override suspend fun analyzeProblem(id: String) = runCatching { api.analyzeProblem(id) }
    override suspend fun verifyResolution(id: String, isResolved: Boolean, rating: Int) = runCatching { api.verifyProblem(id, VerifyRequest(isResolved, rating)); Unit }
}
