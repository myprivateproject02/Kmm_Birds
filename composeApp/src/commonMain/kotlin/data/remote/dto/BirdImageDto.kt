package data.remote.dto

import domain.model.BirdImage
import kotlinx.serialization.Serializable

@Serializable
data class BirdImageDto(
    val category: String,
    val path: String,
    val author: String
)

fun BirdImageDto.toBirdImage(): BirdImage {
    return BirdImage(
        category = category,
        path = path,
        author = author
    )
}