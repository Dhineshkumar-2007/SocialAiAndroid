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
import com.socialai.app.features.auth.CitizenLoginScreen
import com.socialai.app.features.auth.CitizenRegisterScreen
import com.socialai.app.features.citizen.ExploreScreen
import com.socialai.app.features.citizen.MyProblemsScreen
import com.socialai.app.features.citizen.NotificationsScreen
import com.socialai.app.features.citizen.ProblemDetailsFullScreen
import com.socialai.app.features.citizen.ProfileScreen
import com.socialai.app.features.dashboard.AdaptiveLearningScreen
import com.socialai.app.features.dashboard.AdminDashboardScreen
import com.socialai.app.features.dashboard.CitizenHomeScreen
import com.socialai.app.features.dashboard.PublicDashboardScreen
import com.socialai.app.features.matching.MatchResultsScreen
import com.socialai.app.features.matching.ProblemUpdateAcceptedScreen
import com.socialai.app.features.matching.ProblemUpdateNotInterestedScreen
import com.socialai.app.features.matching.TrackProblemScreen
import com.socialai.app.features.matching.UniversityMatchDetailsScreen
import com.socialai.app.features.matching.UniversityMatchingScreen
import com.socialai.app.features.matching.VerificationNotVerifiedScreen
import com.socialai.app.features.matching.VerificationResultScreen
import com.socialai.app.features.onboarding.OnboardingStepScreen
import com.socialai.app.features.problems.AiPipelineScreen
import com.socialai.app.features.problems.ProblemDetailScreen
import com.socialai.app.features.problems.ProblemFeedScreen
import com.socialai.app.features.problems.ReportProblemScreen
import com.socialai.app.features.projects.ProjectWorkspaceScreen
import com.socialai.app.features.reporting.AiLoadingScreen
import com.socialai.app.features.reporting.ReportStartScreen
import com.socialai.app.features.reporting.ReportStep1Screen
import com.socialai.app.features.reporting.ReportStep2Screen
import com.socialai.app.features.reporting.ReportStep3Screen
import com.socialai.app.features.settings.SettingsScreen
import com.socialai.app.features.splash.SplashScreen
import com.socialai.app.features.university.AcceptedProblemsScreen
import com.socialai.app.features.university.DecisionScreen
import com.socialai.app.features.university.IncomingMatchesScreen
import com.socialai.app.features.university.ProblemReviewScreen
import com.socialai.app.features.university.UniversityDashboardScreen
import com.socialai.app.features.university.UniversityLoginScreen
import com.socialai.app.features.verification.VerifyResolutionScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    Scaffold { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(padding)
        ) {
            // Splash & Onboarding
            composable(Screen.Splash.route) { SplashScreen(navController) }
            composable(Screen.Onboarding1.route) { OnboardingStepScreen(navController, step = 1) }
            composable(Screen.Onboarding2.route) { OnboardingStepScreen(navController, step = 2) }
            composable(Screen.Onboarding3.route) { OnboardingStepScreen(navController, step = 3) }

            // Citizen Auth
            composable(Screen.Login.route) { CitizenLoginScreen(navController) }
            composable(Screen.Register.route) { CitizenRegisterScreen(navController) }

            // Citizen Core Screens
            composable(Screen.HomeDashboard.route) { CitizenHomeScreen(navController) }
            composable(Screen.Explore.route) { ExploreScreen(navController) }
            composable(Screen.MyProblems.route) { MyProblemsScreen(navController) }
            composable(Screen.Notifications.route) { NotificationsScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }

            // Report Wizard Screens
            composable(Screen.ReportStart.route) { ReportStartScreen(navController) }
            composable(Screen.ReportStep1.route) { ReportStep1Screen(navController) }
            composable(Screen.ReportStep2.route) { ReportStep2Screen(navController) }
            composable(Screen.ReportStep3.route) { ReportStep3Screen(navController) }
            composable(
                route = Screen.AiLoading.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                AiLoadingScreen(navController, problemId)
            }

            // AI & University Matching Flow
            composable(
                route = Screen.VerificationResult.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                VerificationResultScreen(navController, problemId)
            }
            composable(
                route = Screen.VerificationNotVerified.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                VerificationNotVerifiedScreen(navController, problemId)
            }
            composable(
                route = Screen.UniversityMatching.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                UniversityMatchingScreen(navController, problemId)
            }
            composable(
                route = Screen.MatchResults.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                MatchResultsScreen(navController, problemId)
            }
            composable(
                route = Screen.UniversityMatchDetails.route,
                arguments = listOf(
                    navArgument("problemId") { type = NavType.StringType },
                    navArgument("univId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                val univId = backStackEntry.arguments?.getString("univId") ?: "ANNA_UNIV"
                UniversityMatchDetailsScreen(navController, problemId, univId)
            }
            composable(
                route = Screen.TrackProblem.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                TrackProblemScreen(navController, problemId)
            }
            composable(
                route = Screen.ProblemUpdateAccepted.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                ProblemUpdateAcceptedScreen(navController, problemId)
            }
            composable(
                route = Screen.ProblemUpdateNotInterested.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                ProblemUpdateNotInterestedScreen(navController, problemId)
            }

            // Problem Details Full & Verification
            composable(
                route = Screen.ProblemDetailsFull.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                ProblemDetailsFullScreen(navController, problemId)
            }
            composable(
                route = Screen.VerifyResolution.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                VerifyResolutionScreen(navController, problemId)
            }

            // University App Screens
            composable(Screen.UniversityLogin.route) { UniversityLoginScreen(navController) }
            composable(Screen.UniversityDashboard.route) { UniversityDashboardScreen(navController) }
            composable(Screen.IncomingMatches.route) { IncomingMatchesScreen(navController) }
            composable(
                route = Screen.ProblemReview.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                ProblemReviewScreen(navController, problemId)
            }
            composable(
                route = Screen.Decision.route,
                arguments = listOf(navArgument("problemId") { type = NavType.StringType })
            ) { backStackEntry ->
                val problemId = backStackEntry.arguments?.getString("problemId") ?: "P101"
                DecisionScreen(navController, problemId)
            }
            composable(Screen.AcceptedProblems.route) { AcceptedProblemsScreen(navController) }

            // Secondary / Legacy Routes
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
            composable(Screen.Settings.route) { SettingsScreen(navController) }
        }
    }
}
