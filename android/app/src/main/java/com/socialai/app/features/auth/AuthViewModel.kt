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
    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        data class Success(val role: String) : UiState()
        data class Error(val message: String) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun login(email: String, pass: String) = viewModelScope.launch {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank() || !trimmedEmail.contains("@")) {
            _uiState.value = UiState.Error("Please enter a valid email address.")
            return@launch
        }
        if (pass.isBlank()) {
            _uiState.value = UiState.Error("Please enter your password.")
            return@launch
        }

        _uiState.value = UiState.Loading
        repo.login(trimmedEmail, pass)
            .onSuccess { _uiState.value = UiState.Success(it.user.role) }
            .onFailure { _uiState.value = UiState.Error(it.message ?: "Login failed. Check your credentials.") }
    }

    fun register(
        name: String,
        email: String,
        pass: String,
        role: String,
        phone: String? = null,
        location: String? = null,
        website: String? = null,
        department: String? = null,
        jurisdiction: String? = null,
        orgName: String? = null,
        orgId: String? = null,
        expertise: String? = null,
        capacity: String? = null
    ) = viewModelScope.launch {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim()

        if (trimmedName.isBlank()) {
            _uiState.value = UiState.Error("Please enter your full name or entity name.")
            return@launch
        }
        if (trimmedEmail.isBlank() || !trimmedEmail.contains("@") || !trimmedEmail.contains(".")) {
            _uiState.value = UiState.Error("Please enter a valid official email address.")
            return@launch
        }
        if (pass.length < 6) {
            _uiState.value = UiState.Error("Password must be at least 6 characters long.")
            return@launch
        }

        _uiState.value = UiState.Loading
        repo.register(
            name = trimmedName,
            email = trimmedEmail,
            password = pass,
            role = role,
            phone = phone,
            location = location,
            website = website,
            department = department,
            jurisdiction = jurisdiction,
            orgName = orgName,
            orgId = orgId,
            expertise = expertise,
            capacity = capacity
        )
            .onSuccess { _uiState.value = UiState.Success(it.user.role) }
            .onFailure { _uiState.value = UiState.Error(it.message ?: "Registration failed. Try again.") }
    }

    fun continueAsCitizen(onSuccess: () -> Unit) = viewModelScope.launch {
        repo.continueAsCitizen()
        _uiState.value = UiState.Success("CITIZEN")
        onSuccess()
    }
}
