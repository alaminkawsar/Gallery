package com.example.gallery.data.model


import com.google.gson.annotations.SerializedName

data class RemoteAddress(
    @SerializedName("city")
    val city: String = "",
    @SerializedName("geo")
    val geo: RemoteGeo = RemoteGeo(),
    @SerializedName("street")
    val street: String = "",
    @SerializedName("suite")
    val suite: String = "",
    @SerializedName("zipcode")
    val zipcode: String = ""
)