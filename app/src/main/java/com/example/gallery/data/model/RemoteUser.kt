package com.example.gallery.data.model


import com.example.gallery.domain.model.User
import com.google.gson.annotations.SerializedName

data class RemoteUser(
    @SerializedName("address")
    val address: RemoteAddress = RemoteAddress(),
    @SerializedName("company")
    val company: RemoteCompany = RemoteCompany(),
    @SerializedName("email")
    val email: String = "",
    @SerializedName("id")
    val userId: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("username")
    val userName: String = "",
    @SerializedName("website")
    val website: String = ""
)

fun RemoteUser.toUser(): User {
    return User(
        userId = userId,
        name = name,
        userName = userName
    )
}