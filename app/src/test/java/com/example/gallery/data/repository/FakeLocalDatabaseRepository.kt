package com.example.gallery.data.repository

import com.example.gallery.domain.model.PhotoDataModel
import com.example.gallery.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLocalDatabaseRepository : LocalDatabaseRepository {
    private val photos = mutableListOf<PhotoDataModel>()
    override suspend fun isDataExists(): Boolean {
        return photos.size > 0
    }

    override suspend fun getAllPhoto(): Flow<List<PhotoDataModel>> {
        return flow { emit(photos) }
    }

    override suspend fun addPhoto(photoDataModel: PhotoDataModel) {
        photos.add(photoDataModel)
    }

    override suspend fun addAllPhoto(photoDataModels: List<PhotoDataModel>) {
        photos.addAll(photoDataModels)
    }

    override suspend fun deletePhoto() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPhoto() {
        photos.clear()
    }
}