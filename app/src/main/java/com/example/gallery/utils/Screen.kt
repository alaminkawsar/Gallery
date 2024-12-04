package com.example.gallery.utils

import android.util.Log

const val ROOT_ROUTE = "root_route"
const val MAIN = "main_graph"

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object GalleryScreen: Screen("gallery_screen")
    object ImageShowScreen : Screen("image_show_screen")
    fun withArgs(vararg args: String): String {
        val check = buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}