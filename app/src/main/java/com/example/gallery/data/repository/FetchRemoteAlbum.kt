package com.example.gallery.data.repository

import android.util.Log
import com.example.gallery.data.model.toAlbum
import com.example.gallery.domain.model.Album
import com.example.gallery.domain.repository.RemoteDatabaseRepository
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FetchRemoteAlbum @Inject constructor(
    private val remoteDbRepo: RemoteDatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<Album>>> = flow {
        try {
            val albumListDto = remoteDbRepo.getAlbums()
            val albumList = albumListDto.map { it.toAlbum() }
            Log.d("UserData", "$albumList")

            emit(Resource.Success(albumList))
        } catch (e: HttpException) {
            Log.d("UserData", "${e.message}")
        }
    }

}