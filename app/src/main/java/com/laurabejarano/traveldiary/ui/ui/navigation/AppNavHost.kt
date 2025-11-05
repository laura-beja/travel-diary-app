package com.laurabejarano.traveldiary.ui.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.laurabejarano.traveldiary.ui.ui.screens.CreateLogScreen
import com.laurabejarano.traveldiary.ui.ui.screens.DetailsScreen
import com.laurabejarano.traveldiary.ui.ui.screens.HomeScreen
import com.laurabejarano.traveldiary.ui.ui.screens.SearchScreen
import com.laurabejarano.traveldiary.viewmodel.TravelLogViewModel

/**
 * AppNavHost connects all screens
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: TravelLogViewModel,
    padding: PaddingValues = PaddingValues()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Home.route
    ) {
        // Home Screen
        composable(NavRoutes.Home.route) {
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        // Details Screen
        composable(NavRoutes.Details.route) {
            DetailsScreen(navController = navController, viewModel = viewModel)
        }

        // Create Log Screen
        composable(NavRoutes.Create.route) {
            CreateLogScreen(navController = navController, viewModel = viewModel)
        }

        // Search Screen
//        composable(NavRoutes.Search.route) {
//            SearchScreen(navController = navController, viewModel = viewModel)
//        }
    }
}