package com.example.gallery.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.gallery.utils.MAIN
import com.example.gallery.utils.ROOT_ROUTE
import com.example.gallery.utils.Screen

fun NavGraphBuilder.appNavigation(navController: NavController) {
    navigation(startDestination = Screen.HomeScreen.route, route = MAIN) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreenUI(navController = navController)
        }
        composable(route = Screen.GalleryScreen.route) {
            GalleryScreenUI(navController = navController)
        }
    }
}