package com.socialai.app.features.splash

import androidx.lifecycle.ViewModel
import com.socialai.app.core.datastore.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val sessionManager: SessionManager
) : ViewModel()
