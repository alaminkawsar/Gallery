package com.example.gallery.presentation

import android.app.Application
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery.data.repository.FetchRemoteUserData
import com.example.gallery.domain.model.PhotoDataModel
import com.example.gallery.domain.usecase.PhotosUseCase
import com.example.gallery.presentation.service.DataSyncService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
//    private val repository: PhotoRepository
    private val photosUseCase: PhotosUseCase,
    private val application: Application
) : ViewModel() {

    init {
        viewModelScope.launch {
            startDataSyncService()
        }
    }

//    private val dataSyncReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            val data = intent.getSerializableExtra("data") as? List<PhotoDataModel>
//            data?.let {
//                // Use the data, e.g., update your LiveData or UI
//            }         // Handle the received data (e.g., update LiveData or UI)
//        }
//    }

    init {
//        // Register receiver when ViewModel is created
//        val filter = IntentFilter("com.example.gallery.DATA_SYNC")
//        application.registerReceiver(dataSyncReceiver, filter)
    }
    private fun startDataSyncService() {
        val serviceIntent = Intent(application, DataSyncService::class.java)
        application.startService(serviceIntent)
    }
    private fun stopDataSyncService() {
        val serviceIntent = Intent(application, DataSyncService::class.java)
        application.stopService(serviceIntent)
    }
    override fun onCleared() {
        super.onCleared()
        // Unregister the receiver when ViewModel is cleared
        // application.unregisterReceiver(dataSyncReceiver)
    }
}