package com.example.gallery.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gallery.domain.model.PhotoDataModel

@Entity
data class PhotoModel(
    val photoTitle: String,
    val albumName: String,
    val userName: String,
    val thumbnailUrl: String,
    val photoUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

fun PhotoModel.toPhotoDataModel(): PhotoDataModel {
    return PhotoDataModel(
        photoTitle = photoTitle,
        albumName = albumName,
        userName = userName,
        thumbnailUrl = thumbnailUrl,
        photoUrl = photoUrl
    )
}
