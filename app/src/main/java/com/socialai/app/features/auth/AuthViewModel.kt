package com.socialai.app.features.auth
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialai.app.features.auth.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepository) : ViewModel() {
    sealed class UiState { object Idle : UiState(); object Loading : UiState(); data class Success(val role: String) : UiState(); data class Error(val message: String) : UiState() }
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState
    fun login(email: String, pass: String) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        repo.login(email, pass).onSuccess { _uiState.value = UiState.Success(it.user.role) }.onFailure { _uiState.value = UiState.Error(it.message ?: "Login failed") }
    }
    fun register(name: String, email: String, pass: String, role: String, orgId: String?) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        repo.register(name, email, pass, role, orgId).onSuccess { _uiState.value = UiState.Success(it.user.role) }.onFailure { _uiState.value = UiState.Error(it.message ?: "Register failed") }
    }
    fun continueAsCitizen(onSuccess: () -> Unit) = viewModelScope.launch {
        repo.continueAsCitizen()
        _uiState.value = UiState.Success("CITIZEN")
        onSuccess()
    }
}
