package com.socialai.app.core.data.model
import kotlinx.serialization.Serializable
@Serializable
data class AiAnalysisResult(val category: String? = null, val requiredSkills: List<String> = emptyList(), val evidenceVerdict: String? = null, val evidenceConfidence: Double? = null, val duplicateOf: String? = null, val priorityScore: Double? = null, val priorityLabel: String? = null, val matchedUniversities: List<OrgMatch> = emptyList(), val matchedIndustries: List<OrgMatch> = emptyList())
@Serializable
data class OrgMatch(val orgId: String, val orgName: String, val matchScore: Double, val matchReason: String? = null)
@Serializable
data class AiPipelineStatus(val stage: String, val status: String, val detail: String? = null)
