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
    fun fetchAndSaveRemoteData(): Flow<Resource<String>> = flow {
        // check first local db
        coroutineScope {
            if (dbUseCase.localdb.isDataExists()) {
                val allPhotos = dbUseCase.localdb.getAllPhoto().first()
                emit(Resource.Success("Already Exist"))
                return@coroutineScope
            }
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
            dbUseCase.localdb.addAllPhoto(photoList) // save database in local storage
            emit(Resource.Success("Successfully Saved In Local"))
        }
    }.catch {
//        Log.d("PhotoUseCase", "${it.message}")
    }

    fun getPhotosData(): Flow<Resource<List<PhotoDataModel>>> = flow {
        coroutineScope {
            emit(Resource.Loading())
            try {
                val photos = dbUseCase.localdb.getAllPhoto().first()
                emit(Resource.Success(photos))
            } catch (e: Exception) {
                emit(Resource.Error("Something Wrong"))
            }
        }
    }
    fun deletePhotosData(): Flow<Resource<String>> = flow {
        coroutineScope {
            emit(Resource.Loading())
            try {
                dbUseCase.localdb.deleteAllPhoto()
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(Resource.Error("Something Wrong"))
            }
        }
    }

}