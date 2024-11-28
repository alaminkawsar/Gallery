package com.example.gallery.data.model


import com.google.gson.annotations.SerializedName

data class RemoteCompany(
    @SerializedName("bs")
    val bs: String = "",
    @SerializedName("catchPhrase")
    val catchPhrase: String = "",
    @SerializedName("name")
    val name: String = ""
)