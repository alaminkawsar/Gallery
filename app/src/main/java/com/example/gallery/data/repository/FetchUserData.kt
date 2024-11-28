package com.example.gallery.data.repository

import android.util.Log
import com.example.gallery.data.model.toUser
import com.example.gallery.data.remote.ApiService
import com.example.gallery.domain.model.User
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FetchUserData @Inject constructor(
    private val apiService: ApiService
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            val userListDto = apiService.getUsers()
            val userList = userListDto.map { it.toUser() }
            Log.d("UserData", "${userList}")

            emit(Resource.Success(userList))
        } catch (e: HttpException) {
            Log.d("UserData", "${e.message}")
        }
    }

}