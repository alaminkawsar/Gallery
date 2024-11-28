package com.example.gallery.data.model


import com.example.gallery.domain.model.Photo
import com.google.gson.annotations.SerializedName

data class RemotePhoto(
    @SerializedName("albumId")
    val albumId: Int = 0,
    @SerializedName("id")
    val photoId: Int = 0,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("url")
    val url: String = ""
)

fun RemotePhoto.toPhoto(): Photo {
    return Photo(
        photoId = photoId,
        albumId = albumId,
        photoThumbnailUrl = thumbnailUrl,
        photoTitle = title,
        photoUrl = url
    )
}