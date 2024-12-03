package com.example.gallery.data.repository

import com.example.gallery.data.model.toPhoto
import com.example.gallery.domain.model.Photo
import com.example.gallery.domain.repository.RemoteDatabaseRepository
import com.example.gallery.utils.NetworkError
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class FetchRemotePhoto @Inject constructor(
    private val remoteDbRepo: RemoteDatabaseRepository

) {
    operator fun invoke(): Flow<Resource<List<Photo>>> = flow {
        try {
            val photoListDto = remoteDbRepo.getPhotos()
            val photoList = photoListDto.map { it.toPhoto()}
            emit(Resource.Success(photoList))
        } catch (e: IOException) {
//            Log.e("NetworkError", "No internet connection")
            emit(Resource.Error(NetworkError.CONNECTION_ERROR))
        } catch (e: HttpException) {
            emit(Resource.Error(NetworkError.UNKNOWN_ERROR))
        } catch (e: TimeoutException) {
            emit((Resource.Error(NetworkError.TIMEOUT_ERROR)))
        }
    }

}