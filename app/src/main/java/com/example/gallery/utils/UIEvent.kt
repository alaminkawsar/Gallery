package com.example.gallery.utils

sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()
    data class ShowToastMessage(val message: String) : UIEvent()
}