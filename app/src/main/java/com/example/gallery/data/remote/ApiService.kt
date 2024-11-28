package com.example.gallery.data.remote

import com.example.gallery.data.model.RemoteAlbum
import com.example.gallery.data.model.RemotePhoto
import com.example.gallery.data.model.RemoteUser
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<RemoteUser>
    @GET("albums")
    suspend fun getAlbums(): List<RemoteAlbum>
    @GET("photos")
    suspend fun getPhotos(): List<RemotePhoto>
}