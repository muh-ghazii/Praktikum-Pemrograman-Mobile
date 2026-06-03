package com.contoh.filmapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.contoh.filmapp.ui.screen.DetailScreen
import com.contoh.filmapp.ui.screen.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(route = NavRoutes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(
            route = NavRoutes.Detail.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailScreen(
                movieId = movieId,
                navController = navController
            )
        }
    }
}