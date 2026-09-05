package com.socialai.app.core.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive

object FlexibleStringSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FlexibleString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        return if (decoder is JsonDecoder) {
            val element = decoder.decodeJsonElement()
            if (element is JsonPrimitive) element.content else element.toString()
        } else {
            decoder.decodeString()
        }
    }
}

@Serializable
data class CreateProblemResponse(
    @Serializable(with = FlexibleStringSerializer::class) val problem_id: String? = null,
    @Serializable(with = FlexibleStringSerializer::class) val id: String? = null,
    val message: String? = null
) {
    val createdId: String
        get() = problem_id ?: id ?: ""
}

@Serializable
data class Problem(
    @Serializable(with = FlexibleStringSerializer::class) val id: String = "",
    val title: String = "",
    val description: String = "",
    val district: String = "General",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val status: String = "submitted",
    val category: String? = null,
    @SerialName("priority_level") val priority: String? = null,
    @SerialName("priority_score") val priorityScore: Double? = null,
    val skills: List<String> = emptyList(),
    @SerialName("photo_urls") val photoUrls: List<String> = emptyList(),
    @SerialName("created_by") val reportedBy: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("assigned_institution") val assignedInstitution: String? = null,
    @SerialName("progress_percent") val progressPercent: Int = 0,
    @SerialName("current_stage") val currentStage: String? = null,
    @SerialName("duplicate_similarity") val duplicateSimilarity: Double? = null,
    @SerialName("duplicate_title") val duplicateTitle: String? = null,
    val aiResult: AiAnalysisResult? = null
)
