package com.example.gallery.presentation

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import com.example.gallery.utils.Utilities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val photosUseCase: PhotosUseCase,
    private val application: Application,
    private val preferences: SharedPreferences
) : ViewModel() {

    private val _photosData = mutableStateOf<List<PhotoDataModel>>(emptyList())
    val photosData: State<List<PhotoDataModel>> = _photosData

    private val _isProgressLoading = mutableStateOf(false)
    val isProgressLoading: State<Boolean> = _isProgressLoading

    private val _progressHeaderMessage = mutableStateOf("Please wait")
    val progressHeaderMessage: State<String> = _progressHeaderMessage

    private var _isDeleted = false

    private val _progressDescriptionMessage = mutableStateOf("Please wait")
    val progressDescriptionMessage: State<String> = _progressDescriptionMessage

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val dataSyncReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val data = intent.getStringExtra("data")
            data?.let {
                _isProgressLoading.value = false
                fetchPhotos()
            }
        }
    }

    init {
        // Register receiver when ViewModel is created
        val filter = IntentFilter("com.example.gallery.DATA_SYNC")
        application.registerReceiver(dataSyncReceiver, filter)
        startDataSyncService()

    }

    fun onEvent(event: GalleryEvent) {
        when (event) {
            GalleryEvent.MenuButtonClick -> TODO()
            GalleryEvent.TryAgainButtonClick -> {
                startDataSyncService()
            }

            GalleryEvent.ClearButtonClick -> {
                deleteAllPhotos()
                _isDeleted = true
            }
        }
    }

    private fun startDataSyncService() {
        _isDeleted = false
        val isDatabaseSynced = Utilities.getFlag(preferences)
         printLog(isDatabaseSynced)
        if (isDatabaseSynced) {
            fetchPhotos() // Database already synced
        } else {
            // fetch data using service component
            viewModelScope.launch {
                // check internet connection
                val isInternetAvailable = checkInternetConnection(application.applicationContext)
                if (isInternetAvailable) {
                    _isProgressLoading.value = true
                    _progressHeaderMessage.value = "Please Wait"
                    _progressDescriptionMessage.value = "Syncing..."
                    val serviceIntent = Intent(application, DataSyncService::class.java)
                    application.startService(serviceIntent)
                } else {
                    delay(1) // little delay for showing snack bar message
                    _eventFlow.emit(UIEvent.ShowSnackBar("Please check your internet connection and try again"))
                }
            }

        }
    }

    private fun fetchPhotos() {
        _progressHeaderMessage.value = "Please Wait"
        _progressDescriptionMessage.value = "Photos Loading..."
        viewModelScope.launch {
            photosUseCase.getPhotosData().onEach {
                when (it) {
                    is Resource.Error -> {
                        _isProgressLoading.value = false
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar("Internal Error occurs")
                        )
                    }

                    is Resource.Loading -> {
                        _isProgressLoading.value = true && (!_isDeleted)
                    }

                    is Resource.Success -> {
                        _isProgressLoading.value = false
                        _photosData.value = it.data ?: emptyList()
                        if (_photosData.value.isNotEmpty()) {
                            Utilities.setFlag(preferences)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    private fun deleteAllPhotos() {
        Utilities.clearFlag(preferences)
        printLog(Utilities.getFlag(preferences))
        _progressHeaderMessage.value = "Deleting Photos"
        _progressDescriptionMessage.value = ""
        viewModelScope.launch {
            photosUseCase.deletePhotosData().onEach {
                when (it) {
                    is Resource.Error -> {
                        _isProgressLoading.value = false
                        _eventFlow.emit(UIEvent.ShowSnackBar("Something wrong, please try again"))
                    }

                    is Resource.Loading -> {
                        // _isProgressLoading.value = true
                    }

                    is Resource.Success -> {
                        _isProgressLoading.value = false
                        _eventFlow.emit(UIEvent.ShowSnackBar("Delete Successfully"))
                    }
                }
            }.launchIn(this)
            fetchPhotos()
        }
    }

    fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    override fun onCleared() {
        super.onCleared()
//         Unregister the receiver when ViewModel is cleared
        application.unregisterReceiver(dataSyncReceiver)
    }
    private fun printLog(message: Any) {
        Log.d("GalleryViewModel","$message")
    }
}