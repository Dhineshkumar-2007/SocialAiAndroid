package com.socialai.app.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialai.app.core.datastore.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {
    val serverUrl: StateFlow<String?> = sessionManager.serverUrl
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val userName: StateFlow<String?> = sessionManager.userName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val userRole: StateFlow<String?> = sessionManager.userRole
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isLoggedIn: StateFlow<Boolean> = sessionManager.token
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun saveServerUrl(url: String) = viewModelScope.launch {
        sessionManager.saveServerUrl(url)
    }

    fun logout(onSuccess: () -> Unit) = viewModelScope.launch {
        sessionManager.clearSession()
        onSuccess()
    }
}
