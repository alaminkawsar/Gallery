package com.example.gallery.data.model


import com.google.gson.annotations.SerializedName

data class RemoteGeo(
    @SerializedName("lat")
    val lat: String = "",
    @SerializedName("lng")
    val lng: String = ""
)