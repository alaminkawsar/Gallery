package com.example.gallery.domain.repository

import com.example.gallery.domain.model.PhotoDataModel
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    suspend fun isDataExists(): Boolean
    suspend fun getAllPhoto(): Flow<List<PhotoDataModel>>
    suspend fun addPhoto(photoDataModel: PhotoDataModel)
    suspend fun addAllPhoto(photoDataModels: List<PhotoDataModel>)
    suspend fun deletePhoto()
    suspend fun deleteAllPhoto()

}