package com.socialai.app.features.assignments
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialai.app.core.data.model.*
import com.socialai.app.features.assignments.data.AssignmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AssignmentViewModel @Inject constructor(private val repo: AssignmentRepository) : ViewModel() {
    val inbox = MutableStateFlow<List<Assignment>>(emptyList())
    val selectedAssignment = MutableStateFlow<Assignment?>(null)
    val actionSuccess = MutableStateFlow(false)
    fun loadInbox() = viewModelScope.launch { repo.getInbox().onSuccess { inbox.value = it } }
    fun loadAssignment(id: String) = viewModelScope.launch { repo.getAssignment(id).onSuccess { selectedAssignment.value = it } }
    fun accept(id: String) = viewModelScope.launch { repo.accept(id).onSuccess { actionSuccess.value = true } }
    fun decline(id: String, reason: String) = viewModelScope.launch { repo.decline(id, reason).onSuccess { actionSuccess.value = true } }
}
