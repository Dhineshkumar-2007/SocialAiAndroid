package com.socialai.app.features.problems

import com.socialai.app.core.data.model.CreateProblemResponse
import com.socialai.app.core.data.model.Problem
import com.socialai.app.core.datastore.SessionManager
import com.socialai.app.core.network.CreateProblemRequest
import com.socialai.app.features.problems.data.ProblemRepository
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
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProblemViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository: ProblemRepository = mockk(relaxed = true)
    private val sessionManager: SessionManager = mockk(relaxed = true)
    private lateinit var viewModel: ProblemViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProblemViewModel(repository, sessionManager)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProblems updates problems flow on success`() = runTest {
        val sampleList = listOf(
            Problem(id = "1", title = "Broken Streetlight", district = "Central"),
            Problem(id = "2", title = "Water Leakage", district = "North")
        )
        coEvery { repository.getAllProblems() } returns Result.success(sampleList)

        viewModel.loadProblems(myOnly = false)

        assertEquals(2, viewModel.problems.value.size)
        assertEquals("Broken Streetlight", viewModel.problems.value[0].title)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `createProblem calls repository and triggers callback on success`() = runTest {
        val req = CreateProblemRequest("Road Hazard", "Pothole on Main St", "South", null, null)
        val resp = CreateProblemResponse(problem_id = "prob_123", message = "Created")
        coEvery { repository.createProblem(req) } returns Result.success(resp)

        var createdId: String? = null
        viewModel.createProblem("Road Hazard", "Pothole on Main St", "South", null, null) { id ->
            createdId = id
        }

        assertEquals("prob_123", createdId)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `verifyResolution updates problem verification on success`() = runTest {
        coEvery { repository.verifyResolution("prob_123", true, 5) } returns Result.success(Unit)

        var callbackTriggered = false
        viewModel.verifyResolution("prob_123", isResolved = true, rating = 5) {
            callbackTriggered = true
        }

        assertTrue(callbackTriggered)
        assertFalse(viewModel.isLoading.value)
    }
}
