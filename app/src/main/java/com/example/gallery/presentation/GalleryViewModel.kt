package com.example.gallery.presentation

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery.domain.model.PhotoDataModel
import com.example.gallery.domain.usecase.PhotosUseCase
import com.example.gallery.presentation.service.DataSyncService
import com.example.gallery.utils.Resource
import com.example.gallery.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
//    private val repository: PhotoRepository
    private val photosUseCase: PhotosUseCase,
    private val application: Application
) : ViewModel() {

    private val _photosData = mutableStateOf<List<PhotoDataModel>>(emptyList())
    val photosData: State<List<PhotoDataModel>> = _photosData

    private val _isDatabaseSyncing = mutableStateOf(true)
    val isDatabaseSyncing: State<Boolean> = _isDatabaseSyncing

    private val _isPhotosDataLoading = mutableStateOf(false)
    val isPhotosDataLoading: State<Boolean> = _isPhotosDataLoading

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val dataSyncReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.getStringExtra("data")
            data?.let {
                _isDatabaseSyncing.value = false
                fetchPhotos()
            }
        }
    }

    init {
        // Register receiver when ViewModel is created
        val filter = IntentFilter("com.example.gallery.DATA_SYNC")
        application.registerReceiver(dataSyncReceiver, filter)
        viewModelScope.launch {
            _isDatabaseSyncing.value = true
            startDataSyncService()
        }

    }

    private fun startDataSyncService() {
        val serviceIntent = Intent(application, DataSyncService::class.java)
        application.startService(serviceIntent)
    }

    private fun fetchPhotos() {
        Log.d("ViewModel", "comes")
        viewModelScope.launch {
            photosUseCase.getPhotosData().onEach {
                when (it) {
                    is Resource.Error -> {
                        _isPhotosDataLoading.value = false
                        Log.d("GalleryViewModel", "waiting")
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar("Internal Error occurs")
                        )
                    }
                    is Resource.Loading -> {
                        _isPhotosDataLoading.value = true
                        Log.d("GalleryViewModel", "waiting")
                    }

                    is Resource.Success -> {
                        _isPhotosDataLoading.value = false
                        _photosData.value = it.data ?: emptyList()
                        Log.d("GalleryViewModel", "success: ${it.data}")
                    }
                }
            }.launchIn(this)
        }
    }

    override fun onCleared() {
        super.onCleared()
//         Unregister the receiver when ViewModel is cleared
        application.unregisterReceiver(dataSyncReceiver)
    }
}