package com.example.gallery.domain.usecase

import com.example.gallery.data.repository.FetchRemoteAlbum
import com.example.gallery.data.repository.FetchRemotePhoto
import com.example.gallery.data.repository.FetchRemoteUserData
import com.example.gallery.domain.repository.LocalDatabaseRepository

data class DatabaseUseCase(
    val fetchRemoteAlbum: FetchRemoteAlbum,
    val fetchRemotePhoto: FetchRemotePhoto,
    val fetchRemoteUserData: FetchRemoteUserData,
    val localdb: LocalDatabaseRepository
)
