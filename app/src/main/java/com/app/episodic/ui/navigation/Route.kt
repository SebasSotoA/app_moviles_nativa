package com.app.episodic.ui.navigation

import com.app.episodic.utils.K

sealed class Route {
    // Bottom Navigation Routes
    data class HomeScreen(val route: String = "home") : Route()
    data class ExploreScreen(val route: String = "explore") : Route()
    data class MyListsScreen(val route: String = "my_lists") : Route()
    
    // Genre Route
    data class GenreScreen(
        val route: String = "GenreScreen",
        val routeWithArgs: String = "$route/{genreId}",
    ) : Route() {
        fun getRouteWithArgs(genreId: Int): String {
            return "$route/$genreId"
        }
    }
    
    // Detail Routes
    data class FilmScreen(
        val route: String = "FilmScreen",
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int): String {
            return "$route/$id"
        }
    }
    
    data class TvDetailScreen(
        val route: String = "TvDetailScreen",
        val routeWithArgs: String = "$route/{${K.TV_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int): String {
            return "$route/$id"
        }
    }

    // Custom Lists
    data class AddToListScreen(
        val route: String = "AddToListScreen",
        val routeWithArgs: String = "$route/{itemJson}",
    ) : Route() {
        fun getRouteWithArgs(itemJsonEncoded: String): String {
            return "$route/$itemJsonEncoded"
        }
    }

    data class CreateListScreen(val route: String = "CreateListScreen") : Route()

    data class ListDetailScreen(
        val route: String = "ListDetailScreen",
        val routeWithArgs: String = "$route/{listId}",
    ) : Route() {
        fun getRouteWithArgs(listId: String): String = "$route/$listId"
    }
}