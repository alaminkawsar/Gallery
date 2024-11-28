package com.example.gallery.data.repository

import android.util.Log
import com.example.gallery.data.model.toPhoto
import com.example.gallery.data.model.toUser
import com.example.gallery.data.remote.ApiService
import com.example.gallery.domain.model.Photo
import com.example.gallery.domain.model.User
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FetchPhoto @Inject constructor(
    private val apiService: ApiService
) {
    operator fun invoke(): Flow<Resource<List<Photo>>> = flow {
        try {
            val photoListDto = apiService.getPhotos()
            val photoList = photoListDto.map { it.toPhoto()}
            Log.d("UserData", "${photoList}")

            emit(Resource.Success(photoList))
        } catch (e: HttpException) {
            Log.d("UserData", "${e.message}")
        }
    }

}