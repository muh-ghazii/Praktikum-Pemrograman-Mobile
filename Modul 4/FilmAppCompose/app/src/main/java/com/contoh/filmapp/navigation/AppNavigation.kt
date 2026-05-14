package com.contoh.filmapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.contoh.filmapp.ui.screen.DetailScreen
import com.contoh.filmapp.ui.screen.HomeScreen
import com.contoh.filmapp.ui.viewmodel.MovieViewModel
import com.contoh.filmapp.ui.viewmodel.MovieViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModelFactory = MovieViewModelFactory("Parameter dari Praktikum Modul 4")
    val movieViewModel: MovieViewModel = viewModel(factory = viewModelFactory)

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(route = NavRoutes.Home.route) {
            HomeScreen(navController = navController, viewModel = movieViewModel)
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
                navController = navController,
            )
        }
    }
}