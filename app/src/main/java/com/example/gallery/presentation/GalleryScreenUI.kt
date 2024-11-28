package com.example.gallery.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gallery.presentation.components.PhotoCard

@Composable
fun GalleryScreenUI(
    navController : NavController,
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize()
    ) {
        item {PhotoCard(photoTitle = "A Mew Cat", albumName = "Cats", userName = "Al-Amin Kawsar")}
        item {PhotoCard(photoTitle = "A Mew Cat", albumName = "Cats", userName = "Al-Amin Kawsar")}
        item {PhotoCard(photoTitle = "A Mew Cat", albumName = "Cats", userName = "Al-Amin Kawsar")}
    }
}