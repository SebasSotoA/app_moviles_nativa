package com.app.episodic.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.episodic.ui.detail.MovieDetailScreen
import com.app.episodic.ui.explore.ExploreScreen
import com.app.episodic.ui.genre.GenreScreen
import com.app.episodic.ui.home.HomeScreen
import com.app.episodic.ui.mylists.MyListsScreen
import com.app.episodic.ui.tv_detail.TvDetailScreen
import com.app.episodic.utils.K
import com.app.episodic.custom_lists.presentation.screens.AddToListScreen
import com.app.episodic.custom_lists.presentation.screens.CreateListScreen
import com.app.episodic.custom_lists.presentation.screens.ListDetailScreen
import com.app.episodic.custom_lists.domain.models.ListItem
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun EpisodicNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            // Solo mostrar bottom navigation en las pantallas principales
            if (currentRoute in listOf(
                Route.HomeScreen().route,
                Route.ExploreScreen().route,
                Route.MyListsScreen().route
            )) {
                EpisodicBottomNavigation(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen().route,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Bottom Navigation Screens
            composable(route = Route.HomeScreen().route) {
                HomeScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = movieId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onTvClick = { tvId ->
                        navController.navigate(
                            Route.TvDetailScreen().getRouteWithArgs(id = tvId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onNavigateToHome = {
                        // Limpiar búsqueda cuando se navega a Home
                        // Esto se manejará en HomeScreen
                    },
                    onGenreSelected = { genreId ->
                        navController.navigate(
                            Route.GenreScreen().getRouteWithArgs(genreId = genreId)
                        ) { launchSingleTop = true }
                    }
                )
            }

            composable(route = Route.ExploreScreen().route) {
                ExploreScreen(
                    onSearchClick = {
                        navController.navigate(Route.HomeScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            restoreState = true
                        }
                    },
                    onMovieClick = { movieId ->
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = movieId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onTvClick = { tvId ->
                        navController.navigate(
                            Route.TvDetailScreen().getRouteWithArgs(id = tvId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onGenreClick = { genreId ->
                        navController.navigate(
                            Route.GenreScreen().getRouteWithArgs(genreId = genreId)
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route = Route.MyListsScreen().route) {
                MyListsScreen(
                    onSearchClick = {
                        navController.navigate(Route.HomeScreen().route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            restoreState = true
                        }
                    },
                    onMovieClick = { movieId ->
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = movieId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onTvClick = { tvId ->
                        navController.navigate(
                            Route.TvDetailScreen().getRouteWithArgs(id = tvId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onCreateListClick = {
                        navController.navigate(Route.CreateListScreen().route) {
                            launchSingleTop = true
                        }
                    },
                    onListClick = { listId ->
                        navController.navigate(Route.ListDetailScreen().getRouteWithArgs(listId)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // Detail Screen
            composable(
                route = Route.FilmScreen().routeWithArgs,
                arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
            ) {
                MovieDetailScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onMovieClick = { movieId ->
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = movieId)
                        )
                    },
                    onActorClick = {
                        // TODO: Implementar navegación a perfil de actor
                    },
                    onAddToList = { movieDetail ->
                        val item = ListItem(
                            id = movieDetail.id,
                            title = movieDetail.title,
                            originalTitle = movieDetail.originalTitle,
                            posterPath = movieDetail.posterPath,
                            genreIds = movieDetail.genreIds.mapNotNull { com.app.episodic.utils.GenreConstants.getGenreIdByName(it) },
                            voteAverage = movieDetail.voteAverage,
                            isMovie = true
                        )
                        val json = Json.encodeToString(ListItem.serializer(), item)
                        val encoded = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                        navController.navigate(Route.AddToListScreen().getRouteWithArgs(encoded)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // Genre Screen
            composable(
                route = Route.GenreScreen().routeWithArgs,
                arguments = listOf(navArgument(name = "genreId") { type = NavType.IntType })
            ) {
                val genreId = it.arguments?.getInt("genreId") ?: 0
                GenreScreen(
                    genreId = genreId,
                    onMovieClick = { movieId ->
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = movieId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onTvClick = { tvId ->
                        navController.navigate(
                            Route.TvDetailScreen().getRouteWithArgs(id = tvId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }

            // TV Detail Screen
            composable(
                route = Route.TvDetailScreen().routeWithArgs,
                arguments = listOf(navArgument(name = K.TV_ID) { type = NavType.IntType })
            ) {
                val tvId = it.arguments?.getInt(K.TV_ID) ?: 0
                TvDetailScreen(
                    tvId = tvId,
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onTvClick = { id ->
                        navController.navigate(
                            Route.TvDetailScreen().getRouteWithArgs(id = id)
                        )
                    },
                    onActorClick = {
                        // TODO: Implementar navegación a perfil de actor
                    },
                    onAddToList = { tvDetail ->
                        val item = ListItem(
                            id = tvDetail.id,
                            title = tvDetail.name,
                            originalTitle = tvDetail.originalName,
                            posterPath = tvDetail.posterPath,
                            genreIds = tvDetail.genreIds.mapNotNull { com.app.episodic.utils.GenreConstants.getGenreIdByName(it) },
                            voteAverage = tvDetail.voteAverage,
                            isMovie = false
                        )
                        val json = Json.encodeToString(ListItem.serializer(), item)
                        val encoded = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                        navController.navigate(Route.AddToListScreen().getRouteWithArgs(encoded)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // Add to List Screen
            composable(
                route = Route.AddToListScreen().routeWithArgs,
                arguments = listOf(navArgument(name = "itemJson") { type = NavType.StringType })
            ) { backStackEntry ->
                val encoded = backStackEntry.arguments?.getString("itemJson") ?: ""
                val decodedJson = java.net.URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())
                val item = Json.decodeFromString(ListItem.serializer(), decodedJson)
                AddToListScreen(
                    itemToAdd = item,
                    onClose = { navController.navigateUp() },
                    onCreateListClick = {
                        navController.navigate(Route.CreateListScreen().route) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            // Create List Screen
            composable(route = Route.CreateListScreen().route) {
                CreateListScreen(onBack = { navController.navigateUp() })
            }

            // List Detail Screen
            composable(
                route = Route.ListDetailScreen().routeWithArgs,
                arguments = listOf(navArgument(name = "listId") { type = NavType.StringType })
            ) { backStackEntry ->
                val listId = backStackEntry.arguments?.getString("listId") ?: ""
                ListDetailScreen(
                    listId = listId,
                    onBack = { navController.navigateUp() },
                    onMovieClick = { movieId ->
                        navController.navigate(Route.FilmScreen().getRouteWithArgs(movieId)) {
                            launchSingleTop = true
                        }
                    },
                    onTvClick = { tvId ->
                        navController.navigate(Route.TvDetailScreen().getRouteWithArgs(tvId)) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
