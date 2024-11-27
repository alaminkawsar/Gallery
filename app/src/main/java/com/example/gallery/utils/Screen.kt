package com.example.gallery.utils

const val ROOT_ROUTE = "root_route"
const val MAIN = "main_graph"

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object GalleryScreen: Screen("gallery_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}