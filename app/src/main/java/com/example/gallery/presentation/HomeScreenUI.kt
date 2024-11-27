package com.example.gallery.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gallery.ui.theme.GalleryTheme
import com.example.gallery.utils.Screen

@Composable
fun HomeScreenUI(
    navController: NavController
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "This is Home Screen")
        Button(onClick = {
            navController.navigate(Screen.GalleryScreen.route)
        }) {
            Text(text = "Show Gallery")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenUIPreview() {
    GalleryTheme {
        HomeScreenUI(navController = rememberNavController())
    }

}