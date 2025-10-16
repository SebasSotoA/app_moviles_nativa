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
import com.app.episodic.ui.home.HomeScreen
import com.app.episodic.ui.mylists.MyListsScreen
import com.app.episodic.ui.tv_detail.TvDetailScreen
import com.app.episodic.utils.K

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
                    }
                )
            }

            composable(route = Route.ExploreScreen().route) {
                ExploreScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = movieId)
                        ) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route = Route.MyListsScreen().route) {
                MyListsScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(
                            Route.FilmScreen().getRouteWithArgs(id = movieId)
                        ) {
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
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onActorClick = {
                        // TODO: Implementar navegación a perfil de actor
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
                        ) {
                            launchSingleTop = true
                        }
                    },
                    onActorClick = {
                        // TODO: Implementar navegación a perfil de actor
                    }
                )
            }
        }
    }
}
