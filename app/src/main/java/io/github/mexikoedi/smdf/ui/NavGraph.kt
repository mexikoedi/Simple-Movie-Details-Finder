package io.github.mexikoedi.smdf.ui

import io.github.mexikoedi.smdf.ui.detail.DetailScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.mexikoedi.smdf.ui.search.SearchScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    baseUrl: String
) {
    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchScreen(
                baseUrl = baseUrl,
                onNavigateToDetail = { movieId ->
                    navController.navigate("detail/$movieId")
                }
            )
        }

        composable("detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0

            DetailScreen(
                movieId = movieId,
                baseUrl = baseUrl,
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}