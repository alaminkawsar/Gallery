package com.example.gallery.presentation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.gallery.utils.MAIN
import com.example.gallery.utils.Screen

fun NavGraphBuilder.appNavigation(navController: NavController) {
    navigation(startDestination = Screen.GalleryScreen.route, route = MAIN) {
        composable(route = Screen.GalleryScreen.route) {
            GalleryScreenUI(navController = navController)
        }
        composable(
            route = Screen.ImageShowScreen.route+"/{imageUrl}/{imageTitle}",
            arguments = listOf(
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("imageTitle") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedImageUrl = backStackEntry.arguments?.getString("imageUrl")
            val encodedImageTitle = backStackEntry.arguments?.getString("imageTitle")

            val imageUrl = encodedImageUrl?.let { Uri.decode(it) }
            val imageTitle = encodedImageTitle?.let { Uri.decode(it) }

            ImageShowUI(navController = navController, imageUrl = imageUrl?:"", imageTitle = imageTitle?:"")
        }
    }
}