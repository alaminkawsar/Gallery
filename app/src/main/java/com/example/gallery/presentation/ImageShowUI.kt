package com.example.gallery.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.gallery.presentation.components.PhotoTopAppBar

@Composable
fun ImageShowUI(
    navController: NavController,
    imageUrl: String,
    imageTitle: String
) {
    val scaffoldState = rememberScaffoldState()
    var isLoading by remember { mutableStateOf(true) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            PhotoTopAppBar(imageTitle = imageTitle) {
                navController.popBackStack()
            }
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        ) {
            AsyncImage(
                model = imageUrl,
                contentScale = ContentScale.Fit,
                contentDescription = "Sample image",
                modifier = Modifier.fillMaxSize().padding(8.dp),
                onLoading = {
                    isLoading = true
//                    Log.d("ImageShowUI", "Loading")

                },
                onError = {
                    isLoading = false
//                    Log.d("ImageShowUI", "onError")

                },
                onSuccess = {
                    isLoading = false
//                    Log.d("ImageShowUI", "Success")
                }
            )
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}