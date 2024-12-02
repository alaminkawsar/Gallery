package com.example.gallery.data.repository

import android.util.Log
import com.example.gallery.data.model.toUser
import com.example.gallery.domain.model.User
import com.example.gallery.domain.repository.RemoteDatabaseRepository
import com.example.gallery.utils.NetworkError
import com.example.gallery.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class FetchRemoteUserData @Inject constructor(
    private val remoteDbRepo: RemoteDatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {
        try {
            val userListDto = remoteDbRepo.getUsers()
            val userList = userListDto.map { it.toUser() }
            emit(Resource.Success(userList))
        } catch (e: IOException) {
//            Log.e("NetworkError", "No internet connection")
            emit(Resource.Error(NetworkError.CONNECTION_ERROR))
        } catch (e: HttpException) {
            emit(Resource.Error(NetworkError.UNKNOWN_ERROR))
        } catch (e: TimeoutException) {
            emit((Resource.Error(NetworkError.TIMEOUT_ERROR)))
        }
    }
}