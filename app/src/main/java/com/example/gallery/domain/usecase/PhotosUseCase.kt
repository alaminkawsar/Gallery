package com.example.gallery.domain.usecase

import android.util.Log
import com.example.gallery.domain.model.PhotoDataModel
import com.example.gallery.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotosUseCase @Inject constructor(
    private val dbUseCase: DatabaseUseCase
) {
    fun fetchCombineData(): Flow<Resource<List<PhotoDataModel>>> = flow {
        // check first local db

        coroutineScope {
            // Execute API calls in parallel
            val userDeferred = async { dbUseCase.fetchRemoteUserData().first().data ?: emptyList() }
            val photoDeferred = async { dbUseCase.fetchRemotePhoto().first().data ?: emptyList() }
            val albumDeferred = async { dbUseCase.fetchRemoteAlbum().first().data ?: emptyList() }
            // Await results
            val userMap = userDeferred.await().associateBy { it.userId }
            val albumList = albumDeferred.await().associateBy { it.albumId }
            val photoList = photoDeferred.await().map {
                PhotoDataModel(
                    photoTitle = it.photoTitle,
                    albumName = albumList[it.albumId]?.albumTitle ?: "",
                    userName = userMap[albumList[it.albumId]?.userId]?.userName ?: "",
                    thumbnailUrl = it.photoThumbnailUrl,
                    photoUrl = it.photoUrl
                )
            }
            // make new gallery
            Log.d("PhotosUseCase-1", "$photoList")
            emit(Resource.Success(photoList))
        }
    }.catch {
        Log.d("PhotoUseCase-2", "${it.message}")
    }
}