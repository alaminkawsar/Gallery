package com.example.gallery.data.repository

import com.example.gallery.data.data_source.local.PhotoDao
import com.example.gallery.data.model.PhotoModel
import com.example.gallery.data.model.toPhotoDataModel
import com.example.gallery.domain.model.PhotoDataModel
import com.example.gallery.domain.repository.LocalDatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDatabaseRepositoryImpl(
    private val dao: PhotoDao
) : LocalDatabaseRepository {
    override suspend fun isDataExists(): Boolean {
        return dao.isTableNotEmpty()
    }

    override suspend fun getAllPhoto(): Flow<List<PhotoDataModel>> =
        dao.getPhotos().map { items -> items.map { item -> item.toPhotoDataModel() }
    }

    override suspend fun addPhoto(photoDataModel: PhotoDataModel) {
        TODO("Not yet implemented")
    }

    override suspend fun addAllPhoto(photoDataModels: List<PhotoDataModel>) {
        val photos = photoDataModels.map {
            PhotoModel(
                photoTitle = it.photoTitle,
                albumName = it.albumName,
                userName = it.userName,
                thumbnailUrl = it.thumbnailUrl,
                photoUrl = it.photoUrl
            )
        }
        dao.insertAll(photos)
    }

    override suspend fun deletePhoto() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPhoto() {
        TODO("Not yet implemented")
    }

}