package com.socialai.app.core.navigation

sealed class Screen(val route: String) {
    // Splash & Onboarding
    object Splash : Screen("splash")
    object Onboarding1 : Screen("onboarding/1")
    object Onboarding2 : Screen("onboarding/2")
    object Onboarding3 : Screen("onboarding/3")

    // Citizen Auth
    object Login : Screen("login")
    object Register : Screen("register")

    // Citizen Core Screens
    object HomeDashboard : Screen("home_dashboard")
    object Explore : Screen("explore")
    object MyProblems : Screen("my_problems")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")

    // Report Problem Wizard (4 Steps)
    object ReportStart : Screen("report_start")
    object ReportStep1 : Screen("report_step1")
    object ReportStep2 : Screen("report_step2")
    object ReportStep3 : Screen("report_step3")
    object AiLoading : Screen("ai_loading/{problemId}") {
        fun createRoute(problemId: String) = "ai_loading/$problemId"
    }

    // AI & University Matching Flow
    object VerificationResult : Screen("verification_result/{problemId}") {
        fun createRoute(problemId: String) = "verification_result/$problemId"
    }
    object VerificationNotVerified : Screen("verification_not_verified/{problemId}") {
        fun createRoute(problemId: String) = "verification_not_verified/$problemId"
    }
    object UniversityMatching : Screen("university_matching/{problemId}") {
        fun createRoute(problemId: String) = "university_matching/$problemId"
    }
    object MatchResults : Screen("match_results/{problemId}") {
        fun createRoute(problemId: String) = "match_results/$problemId"
    }
    object UniversityMatchDetails : Screen("university_match_details/{problemId}/{univId}") {
        fun createRoute(problemId: String, univId: String) = "university_match_details/$problemId/$univId"
    }
    object TrackProblem : Screen("track_problem/{problemId}") {
        fun createRoute(problemId: String) = "track_problem/$problemId"
    }
    object ProblemUpdateAccepted : Screen("problem_update_accepted/{problemId}") {
        fun createRoute(problemId: String) = "problem_update_accepted/$problemId"
    }
    object ProblemUpdateNotInterested : Screen("problem_update_not_interested/{problemId}") {
        fun createRoute(problemId: String) = "problem_update_not_interested/$problemId"
    }

    // Problem Details & Resolution Verification
    object ProblemDetailsFull : Screen("problem_details_full/{problemId}") {
        fun createRoute(problemId: String) = "problem_details_full/$problemId"
    }
    object VerifyResolution : Screen("verify_resolution/{problemId}") {
        fun createRoute(problemId: String) = "verify_resolution/$problemId"
    }

    // University App Screens
    object UniversityLogin : Screen("univ_login")
    object UniversityDashboard : Screen("univ_dashboard")
    object IncomingMatches : Screen("univ_incoming_matches")
    object ProblemReview : Screen("univ_problem_review/{problemId}") {
        fun createRoute(problemId: String) = "univ_problem_review/$problemId"
    }
    object Decision : Screen("univ_decision/{problemId}") {
        fun createRoute(problemId: String) = "univ_decision/$problemId"
    }
    object AcceptedProblems : Screen("univ_accepted_problems")

    // Settings & Legacy Compatibility
    object Settings : Screen("settings")
    object ProblemFeed : Screen("problem_feed")
    object ReportProblem : Screen("report_problem")
    object AiPipeline : Screen("ai_pipeline/{problemId}") {
        fun createRoute(problemId: String) = "ai_pipeline/$problemId"
    }
    object ProblemDetail : Screen("problem_detail/{problemId}") {
        fun createRoute(problemId: String) = "problem_detail/$problemId"
    }
    object AssignmentInbox : Screen("assignment_inbox")
    object ChallengeReview : Screen("challenge_review/{assignmentId}") {
        fun createRoute(assignmentId: String) = "challenge_review/$assignmentId"
    }
    object ProjectWorkspace : Screen("project_workspace/{projectId}") {
        fun createRoute(projectId: String) = "project_workspace/$projectId"
    }
    object PublicDashboard : Screen("public_dashboard")
    object AdminDashboard : Screen("admin_dashboard")
    object AdaptiveLearning : Screen("adaptive_learning")
}
