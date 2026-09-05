package com.socialai.app.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassificationDto(
    val category: String? = null,
    val confidence: Double? = null
)

@Serializable
data class PriorityDto(
    val level: String? = null,
    val score: Double? = null
)

@Serializable
data class EvidenceAnalysisDto(
    val filename: String? = null,
    val caption: String? = null,
    @SerialName("evidence_status") val evidenceStatus: String? = null,
    val similarity: Double? = null
)

@Serializable
data class UniversityMatchDto(
    @Serializable(with = FlexibleStringSerializer::class) @SerialName("university_id") val universityId: String? = null,
    val university: String? = null,
    @SerialName("match_percent") val matchPercent: Double? = null,
    @SerialName("final_score") val finalScore: Double? = null,
    val explanation: List<String> = emptyList()
)

@Serializable
data class IndustryMatchDto(
    @Serializable(with = FlexibleStringSerializer::class) @SerialName("industry_id") val industryId: String? = null,
    val industry: String? = null,
    @SerialName("match_percent") val matchPercent: Double? = null,
    @SerialName("final_score") val finalScore: Double? = null,
    val explanation: List<String> = emptyList()
)

@Serializable
data class AiAnalysisResult(
    val classification: ClassificationDto? = null,
    val priority: PriorityDto? = null,
    val skills: List<String> = emptyList(),
    val evidence: List<EvidenceAnalysisDto> = emptyList(),
    val matches: List<UniversityMatchDto> = emptyList(),
    @SerialName("industry_matches") val industryMatches: List<IndustryMatchDto> = emptyList(),
    @SerialName("requiredSkills") val legacySkills: List<String> = emptyList(),
    @SerialName("priorityLabel") val legacyPriorityLabel: String? = null,
    @SerialName("priorityScore") val legacyPriorityScore: Double? = null,
    val categoryName: String? = null
) {
    val category: String?
        get() = classification?.category ?: categoryName

    val requiredSkills: List<String>
        get() = skills.ifEmpty { legacySkills }

    val priorityLabel: String?
        get() = priority?.level ?: legacyPriorityLabel

    val priorityScore: Double?
        get() = priority?.score ?: legacyPriorityScore

    val matchedUniversities: List<OrgMatch>
        get() = matches.map {
            OrgMatch(
                orgId = it.universityId ?: "",
                orgName = it.university ?: "",
                matchScore = (it.matchPercent ?: (it.finalScore?.times(100.0)))?.div(100.0) ?: 0.0,
                matchReason = it.explanation.joinToString(" • ")
            )
        }

    val matchedIndustriesList: List<OrgMatch>
        get() = industryMatches.map {
            OrgMatch(
                orgId = it.industryId ?: "",
                orgName = it.industry ?: "",
                matchScore = (it.matchPercent ?: (it.finalScore?.times(100.0)))?.div(100.0) ?: 0.0,
                matchReason = it.explanation.joinToString(" • ")
            )
        }
}

@Serializable
data class OrgMatch(
    val orgId: String = "",
    val orgName: String = "",
    val matchScore: Double = 0.0,
    val matchReason: String? = null
)

@Serializable
data class AiPipelineStatus(
    val stage: String,
    val status: String,
    val detail: String? = null
)
