package com.socialai.app.features.problems

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialai.app.core.data.model.*
import com.socialai.app.core.datastore.SessionManager
import com.socialai.app.core.network.CreateProblemRequest
import com.socialai.app.features.problems.data.ProblemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val repo: ProblemRepository,
    private val session: SessionManager
) : ViewModel() {
    val problems = MutableStateFlow<List<Problem>>(emptyList())
    val selectedProblem = MutableStateFlow<Problem?>(null)
    val aiResult = MutableStateFlow<AiAnalysisResult?>(null)
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)
    val aiPipelineStages = MutableStateFlow<List<AiPipelineStatus>>(emptyList())

    fun loadProblems(myOnly: Boolean = false) = viewModelScope.launch {
        isLoading.value = true; error.value = null
        val res = if (myOnly) repo.getMyProblems() else repo.getAllProblems()
        res.onSuccess { problems.value = it }.onFailure { error.value = it.message }
        isLoading.value = false
    }

    fun loadProblem(id: String) = viewModelScope.launch {
        isLoading.value = true
        repo.getProblem(id).onSuccess { selectedProblem.value = it }.onFailure { error.value = it.message }
        isLoading.value = false
    }

    fun createProblem(title: String, desc: String, district: String, lat: Double?, lng: Double?, onSuccess: (String) -> Unit) = viewModelScope.launch {
        isLoading.value = true
        repo.createProblem(CreateProblemRequest(title, desc, district, lat, lng)).onSuccess { onSuccess(it.id) }.onFailure { error.value = it.message }
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
        for (i in stages.indices) {
            delay(500)
            currentStatuses[i] = AiPipelineStatus(stages[i], "RUNNING")
            aiPipelineStages.value = currentStatuses.toList()
        }
        repo.analyzeProblem(id).onSuccess { res ->
            aiResult.value = res
            aiPipelineStages.value = stages.map { AiPipelineStatus(it, "DONE") }
        }.onFailure { err ->
            aiPipelineStages.value = stages.map { AiPipelineStatus(it, "ERROR", err.message) }
        }
    }
}
