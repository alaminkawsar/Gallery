package com.example.gallery.data.repository

import android.util.Log
import com.example.gallery.data.model.toUser
import com.example.gallery.domain.model.User
import com.example.gallery.domain.repository.RemoteDatabaseRepository
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class FetchRemoteUserData @Inject constructor(
    private val remoteDbRepo: RemoteDatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        Log.d("FetchRemoteUserData","Reached")
        try {
            val userListDto = remoteDbRepo.getUsers()
            Log.d("FetchRemoteUserData","Reached2")
            val userList = userListDto.map { it.toUser() }
            emit(Resource.Success(userList))
        } catch (e: SocketTimeoutException) {
            Log.e("Timeout", "Connection timed out")
        } catch (e: HttpException) {
            Log.d("UserData", "${e.message}")
            emit(Resource.Error("err"))
        }
    }
}