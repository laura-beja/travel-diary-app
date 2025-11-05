package com.laurabejarano.traveldiary.ui.ui.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Details : NavRoutes("details")
    object Create : NavRoutes("create")
    object Search : NavRoutes("search")
}
