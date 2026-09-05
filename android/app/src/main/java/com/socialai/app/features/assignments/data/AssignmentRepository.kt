package com.socialai.app.features.assignments.data
import com.socialai.app.core.data.model.*
import com.socialai.app.core.network.ApiService
import com.socialai.app.core.network.DeclineRequest
import javax.inject.Inject
interface AssignmentRepository {
    suspend fun getInbox(): Result<List<Assignment>>
    suspend fun getAssignment(id: String): Result<Assignment>
    suspend fun accept(id: String): Result<Assignment>
    suspend fun decline(id: String, reason: String): Result<Assignment>
}
class AssignmentRepositoryImpl @Inject constructor(private val api: ApiService) : AssignmentRepository {
    override suspend fun getInbox() = runCatching { api.getAssignmentInbox() }
    override suspend fun getAssignment(id: String) = runCatching { api.getAssignment(id) }
    override suspend fun accept(id: String) = runCatching { api.acceptAssignment(id) }
    override suspend fun decline(id: String, reason: String) = runCatching { api.declineAssignment(id, DeclineRequest(reason)) }
}
