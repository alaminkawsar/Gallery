package com.example.gallery.presentation

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gallery.presentation.components.CustomTopAppBar
import com.example.gallery.presentation.components.PhotoCard
import com.example.gallery.utils.Screen
import com.example.gallery.utils.TestTag
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
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar() {
                galleryViewModel.onEvent(GalleryEvent.ClearButtonClick)
            }
        }
    ) {
        if (galleryViewModel.isProgressLoading.value) {
            CustomProgressMessage(
                header = galleryViewModel.progressHeaderMessage.value,
                description = galleryViewModel.progressDescriptionMessage.value
            )
        } else {
            if (galleryViewModel.photosData.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = "No photo available to show",
                            color = Color.Gray,
                            modifier = Modifier.testTag(TestTag.NO_PHOTO)
                        )
                        Spacer(modifier = Modifier.width(50.dp))
                        Button(
                            onClick = {
                                galleryViewModel.onEvent(GalleryEvent.TryAgainButtonClick)
                            },
                            modifier = Modifier.testTag(TestTag.TRY_AGAIN_BUTTON)
                        ) {
                            Text(text = "Try Again")
                        }
                    }

                }
            } else {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(TestTag.SCROLL_VIEW),
                    contentPadding = it
                ) {

                    galleryViewModel.photosData.value.forEach {
                        item {
                            PhotoCard(
                                photoTitle = it.photoTitle,
                                albumName = it.albumName,
                                userName = it.userName,
                                thumbnailUrl = it.thumbnailUrl
                            ) {
                                navController.navigate(Screen.ImageShowScreen.withArgs(Uri.encode(it.photoUrl), Uri.encode(it.photoTitle)))
                            }
                        }
                    }
                }
            }

        }
    }

}