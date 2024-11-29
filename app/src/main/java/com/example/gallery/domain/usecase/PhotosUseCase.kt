package com.example.gallery.domain.usecase

import android.util.Log
import com.example.gallery.data.model.PhotoModel
import com.example.gallery.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PhotosUseCase @Inject constructor(
    private val dbUseCase: DatabaseUseCase
){
    suspend fun fetchCombineData() {
        // check first local db

        try {
            coroutineScope {
                // Execute API calls in parallel
                val userDeferred = async { dbUseCase.fetchRemoteUserData().first().data?: emptyList()}
                val photoDeferred = async { dbUseCase.fetchRemotePhoto().first().data?: emptyList()}
                val albumDeferred = async { dbUseCase.fetchRemoteAlbum().first().data?: emptyList()}
                // Await results
                val userMap = userDeferred.await().associateBy { it.userId }
                val albumList = albumDeferred.await().associateBy { it.albumId }
                val photoList = photoDeferred.await().map {
                    PhotoModel(
                        photoTitle = it.photoTitle,
                        albumName = albumList[it.albumId]?.albumTitle?:"",
                        userName = userMap[albumList[it.albumId]?.userId]?.userName?:"",
                        thumbnailUrl = it.photoThumbnailUrl,
                        photoUrl = it.photoUrl
                    )
                }
                // make new gallery
                Log.d("PhotosUseCase","$photoList")

                // emit(Resource.Success(Triple(userList, photoList, albumList)))
            }
        } catch (e: Exception) {
            // emit(Resource.Error("Error combining data: ${e.message}"))
        }
    }
}