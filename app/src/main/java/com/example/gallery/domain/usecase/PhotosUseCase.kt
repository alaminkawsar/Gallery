package com.example.gallery.domain.usecase

import javax.inject.Inject

class PhotosUseCase @Inject constructor(
    private val dbUseCase: DatabaseUseCase
){
    fun fetchCombineData() {
        // check first local db
        val data = dbUseCase.localdb.getPhotos()
        val userList = dbUseCase.fetchRemoteUserData
        val photoList = dbUseCase.fetchRemotePhoto
        val albumList = dbUseCase.fetchRemoteAlbum
    }
}