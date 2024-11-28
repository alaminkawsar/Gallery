package com.example.gallery.domain.model

import com.google.gson.annotations.SerializedName

data class Photo(
    val albumId: Int = 0,
    val photoId: Int = 0,
    val photoThumbnailUrl: String = "",
    val photoTitle: String = "",
    val photoUrl: String = ""
)
