package com.example.gallery.data.repository

import android.util.Log
import com.example.gallery.data.model.toAlbum
import com.example.gallery.domain.model.Album
import com.example.gallery.domain.repository.RemoteDatabaseRepository
import com.example.gallery.utils.NetworkError
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class FetchRemoteAlbum @Inject constructor(
    private val remoteDbRepo: RemoteDatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<Album>>> = flow {
        try {
            val albumListDto = remoteDbRepo.getAlbums()
            val albumList = albumListDto.map { it.toAlbum() }
            emit(Resource.Success(albumList))
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