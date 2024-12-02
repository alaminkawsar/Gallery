package com.example.gallery.presentation

sealed class GalleryEvent {
    data object TryAgainButtonClick: GalleryEvent()
    data object MenuButtonClick: GalleryEvent()
    data object ClearButtonClick: GalleryEvent()
}