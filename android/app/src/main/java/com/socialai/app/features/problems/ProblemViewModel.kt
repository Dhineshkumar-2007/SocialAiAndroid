package com.socialai.app.features.problems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialai.app.core.data.model.*
import com.socialai.app.core.datastore.SessionManager
import com.socialai.app.core.network.CreateProblemRequest
import com.socialai.app.features.problems.data.ProblemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val repo: ProblemRepository,
    val session: SessionManager
) : ViewModel() {
    val problems = MutableStateFlow<List<Problem>>(emptyList())
    val selectedProblem = MutableStateFlow<Problem?>(null)
    val aiResult = MutableStateFlow<AiAnalysisResult?>(null)
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)
    val aiPipelineStages = MutableStateFlow<List<AiPipelineStatus>>(emptyList())

    // Real User State
    val userName = MutableStateFlow<String?>(null)
    val userRole = MutableStateFlow<String?>(null)

    // Real Report Problem Draft State
    val draftTitle = MutableStateFlow("")
    val draftDesc = MutableStateFlow("")
    val draftCategory = MutableStateFlow("")
    val draftLocation = MutableStateFlow("")
    val draftEvidence = MutableStateFlow<List<String>>(emptyList())

    init {
        viewModelScope.launch {
            try {
                session.userName.collect { userName.value = it }
            } catch (e: Exception) {
                // Ignore if unconfigured in tests
            }
        }
        viewModelScope.launch {
            try {
                session.userRole.collect { userRole.value = it }
            } catch (e: Exception) {
                // Ignore if unconfigured in tests
            }
        }
    }

    fun setDraftDetails(title: String, desc: String, category: String) {
        draftTitle.value = title
        draftDesc.value = desc
        draftCategory.value = category
    }

    fun setDraftLocation(location: String) {
        draftLocation.value = location
    }

    fun setDraftEvidence(evidence: List<String>) {
        draftEvidence.value = evidence
    }

    fun clearDraft() {
        draftTitle.value = ""
        draftDesc.value = ""
        draftCategory.value = ""
        draftLocation.value = ""
        draftEvidence.value = emptyList()
    }

    fun loadProblems(myOnly: Boolean = false) = viewModelScope.launch {
        isLoading.value = true; error.value = null
        val res = if (myOnly) repo.getMyProblems() else repo.getAllProblems()
        res.onSuccess { problems.value = it }.onFailure { error.value = it.message }
        isLoading.value = false
    }

    fun loadProblem(id: String) = viewModelScope.launch {
        isLoading.value = true
        val problemRes = repo.getProblem(id)
        problemRes.onSuccess { problem ->
            val analysisRes = repo.analyzeProblem(id).getOrNull()
            val combinedProblem = if (analysisRes != null) {
                aiResult.value = analysisRes
                problem.copy(aiResult = analysisRes)
            } else {
                problem
            }
            selectedProblem.value = combinedProblem
        }.onFailure {
            error.value = it.message
        }
        isLoading.value = false
    }

    fun submitDraftProblem(onSuccess: (String) -> Unit) = viewModelScope.launch {
        isLoading.value = true
        val request = CreateProblemRequest(
            title = draftTitle.value,
            description = draftDesc.value,
            district = if (draftLocation.value.isNotBlank()) draftLocation.value else "General Location",
            latitude = null,
            longitude = null
        )
        repo.createProblem(request)
            .onSuccess { response ->
                val newId = response.createdId.ifBlank { "P_${System.currentTimeMillis()}" }
                onSuccess(newId)
            }
            .onFailure { err ->
                error.value = err.message
            }
        isLoading.value = false
    }

    fun createProblem(title: String, desc: String, district: String, lat: Double?, lng: Double?, onSuccess: (String) -> Unit) = viewModelScope.launch {
        isLoading.value = true
        repo.createProblem(CreateProblemRequest(title, desc, district, lat, lng)).onSuccess { onSuccess(it.createdId) }.onFailure { error.value = it.message }
        isLoading.value = false
    }

    fun verifyResolution(id: String, isResolved: Boolean, rating: Int, onSuccess: () -> Unit) = viewModelScope.launch {
        isLoading.value = true
        repo.verifyResolution(id, isResolved, rating).onSuccess { onSuccess() }.onFailure { error.value = it.message }
        isLoading.value = false
    }

    fun analyzeProblem(id: String) = viewModelScope.launch {
        val stages = listOf("embedding", "classification", "skills", "evidence", "duplicates", "priority", "matching")
        val currentStatuses = stages.map { AiPipelineStatus(it, "PENDING") }.toMutableList()
        aiPipelineStages.value = currentStatuses.toList()
        isLoading.value = true
        error.value = null

        val networkJob = async { repo.analyzeProblem(id) }

        for (i in stages.indices) {
            currentStatuses[i] = AiPipelineStatus(stages[i], "RUNNING")
            aiPipelineStages.value = currentStatuses.toList()
            delay(150)
            currentStatuses[i] = AiPipelineStatus(stages[i], "DONE")
            aiPipelineStages.value = currentStatuses.toList()
        }

        val res = networkJob.await()
        res.onSuccess { result ->
            aiResult.value = result
            aiPipelineStages.value = stages.map { AiPipelineStatus(it, "DONE") }
        }.onFailure { err ->
            error.value = err.message
            aiPipelineStages.value = stages.map { AiPipelineStatus(it, "DONE") }
        }
        isLoading.value = false
    }
}
