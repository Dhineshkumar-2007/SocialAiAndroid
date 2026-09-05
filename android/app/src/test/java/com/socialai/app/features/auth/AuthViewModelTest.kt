package com.socialai.app.features.auth

import com.socialai.app.core.data.model.AuthResponse
import com.socialai.app.core.data.model.User
import com.socialai.app.features.auth.data.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository: AuthRepository = mockk(relaxed = true)
    private lateinit var viewModel: AuthViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AuthViewModel(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with invalid email sets error state`() = runTest {
        viewModel.login("invalid-email", "password123")
        val state = viewModel.uiState.value
        assertTrue(state is AuthViewModel.UiState.Error)
        assertEquals("Please enter a valid email address.", (state as AuthViewModel.UiState.Error).message)
    }

    @Test
    fun `login with empty password sets error state`() = runTest {
        viewModel.login("valid@example.com", "")
        val state = viewModel.uiState.value
        assertTrue(state is AuthViewModel.UiState.Error)
        assertEquals("Please enter your password.", (state as AuthViewModel.UiState.Error).message)
    }

    @Test
    fun `register with short password sets error state`() = runTest {
        viewModel.register("John Doe", "john@example.com", "123", "CITIZEN")
        val state = viewModel.uiState.value
        assertTrue(state is AuthViewModel.UiState.Error)
        assertEquals("Password must be at least 6 characters long.", (state as AuthViewModel.UiState.Error).message)
    }

    @Test
    fun `login success sets success state`() = runTest {
        val user = User(id = "1", name = "Test User", email = "test@example.com", role = "citizen")
        coEvery { repository.login("test@example.com", "password123") } returns Result.success(AuthResponse("token", user))

        viewModel.login("test@example.com", "password123")

        val state = viewModel.uiState.value
        assertTrue(state is AuthViewModel.UiState.Success)
        assertEquals("citizen", (state as AuthViewModel.UiState.Success).role)
    }
}
