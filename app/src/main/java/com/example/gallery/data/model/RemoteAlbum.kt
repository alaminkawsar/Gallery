package com.example.gallery.data.model


import com.example.gallery.domain.model.Album
import com.google.gson.annotations.SerializedName

data class RemoteAlbum(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("userId")
    val userId: Int = 0
)

fun RemoteAlbum.toAlbum(): Album {
    return Album(
        albumId = id,
        albumTitle = title,
        userId = userId
    )
}