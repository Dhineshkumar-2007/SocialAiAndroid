package com.socialai.app.features.projects
import com.socialai.app.core.data.model.*
import com.socialai.app.core.network.ApiService
import com.socialai.app.core.network.CreateMilestoneRequest
import javax.inject.Inject
interface ProjectRepository {
    suspend fun getProjects(): Result<List<Project>>
    suspend fun getProject(id: String): Result<Project>
    suspend fun createMilestone(projectId: String, title: String, description: String?, dueDate: String?): Result<Milestone>
}
class ProjectRepositoryImpl @Inject constructor(private val api: ApiService) : ProjectRepository {
    override suspend fun getProjects() = runCatching { api.getProjects() }
    override suspend fun getProject(id: String) = runCatching { api.getProject(id) }
    override suspend fun createMilestone(projectId: String, title: String, description: String?, dueDate: String?) = runCatching { api.createMilestone(projectId, CreateMilestoneRequest(title, description, dueDate)) }
}
