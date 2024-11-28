package com.example.gallery.domain.repository

import com.example.gallery.domain.model.PhotoDataModel
import kotlinx.coroutines.flow.Flow

interface LocalDatabaseRepository {
    fun getPhotos(): Flow<List<PhotoDataModel>>
    suspend fun addPhotos(photoDataModels: List<PhotoDataModel>)
}