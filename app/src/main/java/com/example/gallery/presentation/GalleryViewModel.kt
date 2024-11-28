package com.example.gallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery.data.repository.FetchRemoteUserData
import com.example.gallery.domain.usecase.PhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
//    private val repository: PhotoRepository
    private val fetchUserData: FetchRemoteUserData, // Inject FetchUserData
    private val photosUseCase: PhotosUseCase
) : ViewModel() {

    init {
        fetchData()
    }
    private fun fetchData() {
        viewModelScope.launch {
            photosUseCase.fetchCombineData()
        }

    }
}