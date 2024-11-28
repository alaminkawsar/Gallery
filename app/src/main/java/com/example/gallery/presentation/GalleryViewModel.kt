package com.example.gallery.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gallery.data.repository.FetchUserData
import com.example.gallery.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
//    private val repository: PhotoRepository
    private val fetchUserData: FetchUserData // Inject FetchUserData
) : ViewModel() {

    init {
        fetchData()
    }
    private fun fetchData() {
        viewModelScope.launch {
            fetchUserData().onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        // Handle the success case, e.g., update UI
                        val users = resource.data
                        println("Fetched users: $users")
                    }
                    is Resource.Error -> {
                        // Handle the error case, e.g., show error message
                        println("Error: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        // Handle loading state, e.g., show progress bar
                        println("Loading...")
                    }
                }
            }.launchIn(this) // Launch the Flow in the ViewModel scope
        }

    }
}