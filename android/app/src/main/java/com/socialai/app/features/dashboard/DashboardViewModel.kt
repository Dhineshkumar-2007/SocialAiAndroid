package com.socialai.app.features.dashboard
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialai.app.core.data.model.DashboardStats
import com.socialai.app.core.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DashboardViewModel @Inject constructor(private val api: ApiService) : ViewModel() {
    val publicStats = MutableStateFlow<DashboardStats?>(null)
    fun loadPublicStats() = viewModelScope.launch { runCatching { api.getDashboardStats() }.onSuccess { publicStats.value = it } }
}
