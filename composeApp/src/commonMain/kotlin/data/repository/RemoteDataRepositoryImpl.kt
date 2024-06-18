package data.repository

import common.Constant.BASE_URL
import common.Constant.END_POINT
import data.remote.dto.BirdImageDto
import domain.repository.RemoteDataRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteDataRepositoryImpl(
    private val endPoint: String,
    private val httpClient: HttpClient,
) : RemoteDataRepository {

    override suspend fun getImages(): List<BirdImageDto> {
        val result = httpClient.get("${BASE_URL}${END_POINT}").body<List<BirdImageDto>>()
        return result
    }

}