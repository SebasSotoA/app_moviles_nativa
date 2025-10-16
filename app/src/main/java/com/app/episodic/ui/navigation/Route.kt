package com.app.episodic.ui.navigation

import com.app.episodic.utils.K

sealed class Route {
    // Bottom Navigation Routes
    data class HomeScreen(val route: String = "home") : Route()
    data class ExploreScreen(val route: String = "explore") : Route()
    data class MyListsScreen(val route: String = "my_lists") : Route()

    data class Lists(val route: String = "lists") : Route()

    // Detail Routes
    data class FilmScreen(
            val route: String = "FilmScreen",
            val routeWithArgs: String = "$route/{${K.MOVIE_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int): String {
            return "$route/$id"
        }
    }
}
