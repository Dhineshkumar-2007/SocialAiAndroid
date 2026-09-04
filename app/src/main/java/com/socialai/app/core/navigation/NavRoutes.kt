package com.socialai.app.core.navigation
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ProblemFeed : Screen("problem_feed")
    object ReportProblem : Screen("report_problem")
    object AiPipeline : Screen("ai_pipeline/{problemId}") { fun createRoute(problemId: String) = "ai_pipeline/$problemId" }
    object ProblemDetail : Screen("problem_detail/{problemId}") { fun createRoute(problemId: String) = "problem_detail/$problemId" }
    object AssignmentInbox : Screen("assignment_inbox")
    object ChallengeReview : Screen("challenge_review/{assignmentId}") { fun createRoute(assignmentId: String) = "challenge_review/$assignmentId" }
    object ProjectWorkspace : Screen("project_workspace/{projectId}") { fun createRoute(projectId: String) = "project_workspace/$projectId" }
    object PublicDashboard : Screen("public_dashboard")
    object AdminDashboard : Screen("admin_dashboard")
    object AdaptiveLearning : Screen("adaptive_learning")
    object VerifyResolution : Screen("verify_resolution/{problemId}") { fun createRoute(problemId: String) = "verify_resolution/$problemId" }
    object Settings : Screen("settings")
}
