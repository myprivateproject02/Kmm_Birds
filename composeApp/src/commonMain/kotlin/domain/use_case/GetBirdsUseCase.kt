package domain.use_case

import common.Resource
import data.remote.dto.toBirdImage
import domain.model.BirdImage
import domain.repository.RemoteDataRepository
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetBirdsUseCase(
    private val repository: RemoteDataRepository,
) {
    fun getImages(): Flow<Resource<List<BirdImage>>> = flow {
        try {
            emit(Resource.Loading())
            val birdImageList = repository.getImages().map {
                it.toBirdImage()
            }
            emit(Resource.Success(birdImageList))
        } catch (e: IOException) {
            println("IOException called $e")
            emit(Resource.Error("Something Went Wrong!"))
        }
    }.flowOn(Dispatchers.IO)
}
