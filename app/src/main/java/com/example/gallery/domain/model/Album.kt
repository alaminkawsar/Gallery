package com.example.gallery.domain.model


import com.google.gson.annotations.SerializedName

data class Album(
    val albumId: Int = 0,
    val albumTitle: String = "",
    val userId: Int = 0
)