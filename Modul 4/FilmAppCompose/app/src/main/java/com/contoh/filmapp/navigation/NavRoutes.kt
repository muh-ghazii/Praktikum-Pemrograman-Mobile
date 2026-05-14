package com.contoh.filmapp.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Detail : NavRoutes("detail/{movieId}") {
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}