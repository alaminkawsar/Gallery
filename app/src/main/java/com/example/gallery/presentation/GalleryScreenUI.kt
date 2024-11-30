package com.example.gallery.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gallery.presentation.components.CustomTopAppBar
import com.example.gallery.presentation.components.PhotoCard
import com.example.gallery.utils.UIEvent
import com.example.gallery.utils.components.CustomProgressMessage

@Composable
fun GalleryScreenUI(
    navController: NavController,
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        galleryViewModel.eventFlow.collect { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }

                is UIEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            CustomTopAppBar() {
                navController.popBackStack()
            }
        }
    ) {
        if (galleryViewModel.isDatabaseSyncing.value or galleryViewModel.isPhotosDataLoading.value) {
            CustomProgressMessage(
                header = "Please Wait",
                description = if (galleryViewModel.isDatabaseSyncing.value) {
                    "Database Syncing"
                } else {
                    "Loading Photo"
                }
            )
        } else {
            if (galleryViewModel.photosData.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No photo available to show",
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = it
                ) {

                    galleryViewModel.photosData.value.forEach {
                        item {
                            PhotoCard(
                                photoTitle = it.photoTitle,
                                albumName = it.albumName,
                                userName = it.userName
                            )
                        }
                    }
                }
            }

        }
    }

}