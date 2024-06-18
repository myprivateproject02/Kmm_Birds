package domain.repository

import data.remote.dto.BirdImageDto

interface RemoteDataRepository {
    suspend fun getImages(): List<BirdImageDto>
}