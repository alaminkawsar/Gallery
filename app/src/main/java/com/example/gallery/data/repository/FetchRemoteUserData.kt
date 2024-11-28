package com.example.gallery.data.repository

import android.util.Log
import com.example.gallery.data.model.toUser
import com.example.gallery.domain.model.User
import com.example.gallery.domain.repository.RemoteDatabaseRepository
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FetchRemoteUserData @Inject constructor(
    private val remoteDbRepo: RemoteDatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            val userListDto = remoteDbRepo.getUsers()
            val userList = userListDto.map { it.toUser() }
        } catch (e: HttpException) {
            Log.d("UserData", "${e.message}")
        }
    }
}