package com.socialai.app.core.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.socialai.app.features.assignments.AssignmentInboxScreen
import com.socialai.app.features.assignments.ChallengeReviewScreen
import com.socialai.app.features.auth.LoginScreen
import com.socialai.app.features.auth.RegisterScreen
import com.socialai.app.features.dashboard.AdaptiveLearningScreen
import com.socialai.app.features.dashboard.AdminDashboardScreen
import com.socialai.app.features.dashboard.PublicDashboardScreen
import com.socialai.app.features.problems.AiPipelineScreen
import com.socialai.app.features.problems.ProblemDetailScreen
import com.socialai.app.features.problems.ProblemFeedScreen
import com.socialai.app.features.problems.ReportProblemScreen
import com.socialai.app.features.projects.ProjectWorkspaceScreen
import com.socialai.app.features.settings.SettingsScreen
import com.socialai.app.features.verification.VerifyResolutionScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    Scaffold { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.Register.route) { RegisterScreen(navController) }
            composable(Screen.ProblemFeed.route) { ProblemFeedScreen(navController) }
            composable(Screen.ReportProblem.route) { ReportProblemScreen(navController) }
            composable(
                route = Screen.AiPipeline.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: ""
                AiPipelineScreen(navController, problemId)
            }
            composable(
                route = Screen.ProblemDetail.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: ""
                ProblemDetailScreen(navController, problemId)
            }
            composable(Screen.AssignmentInbox.route) { AssignmentInboxScreen(navController) }
            composable(
                route = Screen.ChallengeReview.route,
                arguments = listOf(navArgument("assignmentId") { type = NavType.StringType })
            ) { backStackEntry ->
                val assignmentId = backStackEntry.arguments?.getString("assignmentId") ?: ""
                ChallengeReviewScreen(navController, assignmentId)
            }
            composable(
                route = Screen.ProjectWorkspace.route,
                arguments = listOf(navArgument("projectId") { type = NavType.StringType })
            ) { backStackEntry ->
                val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
                ProjectWorkspaceScreen(navController, projectId)
            }
            composable(Screen.PublicDashboard.route) { PublicDashboardScreen() }
            composable(Screen.AdminDashboard.route) { AdminDashboardScreen() }
            composable(Screen.AdaptiveLearning.route) { AdaptiveLearningScreen() }
            composable(
                route = Screen.VerifyResolution.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: ""
                VerifyResolutionScreen(navController, problemId)
            }
            composable(Screen.Settings.route) { SettingsScreen(navController) }
        }
    }
}
