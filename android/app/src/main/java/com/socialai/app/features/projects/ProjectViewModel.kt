package com.socialai.app.features.projects
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialai.app.core.data.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProjectViewModel @Inject constructor(private val repo: ProjectRepository) : ViewModel() {
    val selectedProject = MutableStateFlow<Project?>(null)
    fun loadProject(id: String) = viewModelScope.launch { repo.getProject(id).onSuccess { selectedProject.value = it } }
}
