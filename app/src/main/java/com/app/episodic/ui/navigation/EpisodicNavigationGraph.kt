package com.app.episodic.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.app.episodic.ui.lists.ListsScreen
import com.app.episodic.ui.lists.create.CreateListScreen
import com.app.episodic.ui.mylists.MyListsScreen
import com.app.episodic.ui.splash.SplashScreen
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
                if (currentRoute in
                                listOf(
                                        Route.HomeScreen().route,
                                        Route.ExploreScreen().route,
                                        Route.MyListsScreen().route
                                )
                ) {
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
                startDestination = Route.Splash().route,
                modifier = modifier.fillMaxSize().padding(innerPadding)
        ) {
            // Splash Screen
            composable(route = Route.Splash().route) {
                SplashScreen(
                    onFinished = {
                        navController.navigate(Route.HomeScreen().route) {
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
            // Bottom Navigation Screens
            composable(route = Route.HomeScreen().route) {
                HomeScreen(
                        onMovieClick = { movieId ->
                            navController.navigate(
                                    Route.FilmScreen().getRouteWithArgs(id = movieId)
                            ) { launchSingleTop = true }
                        }
                )
            }

            composable(route = Route.ExploreScreen().route) {
                ExploreScreen(
                        onMovieClick = { movieId ->
                            navController.navigate(
                                    Route.FilmScreen().getRouteWithArgs(id = movieId)
                            ) { launchSingleTop = true }
                        }
                )
            }

            composable(route = Route.MyListsScreen().route) {
                MyListsScreen(
                        onMovieClick = { movieId ->
                            navController.navigate(
                                    Route.FilmScreen().getRouteWithArgs(id = movieId)
                            ) { launchSingleTop = true }
                        }
                )
            }

            composable(Route.Lists().route) { 
                ListsScreen(
                    onCreateList = {
                        navController.navigate(Route.CreateList().route)
                    }
                )
            }
            
            composable(Route.CreateList().route) {
                CreateListScreen(
                    onClose = { navController.popBackStack() },
                    onCreated = { newName ->
                        navController.popBackStack()
                        // TODO: Mostrar snackbar de éxito
                    }
                )
            }

            // Detail Screen
            composable(
                    route = Route.FilmScreen().routeWithArgs,
                    arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
            ) {
                MovieDetailScreen(
                        onNavigateUp = { navController.navigateUp() },
                        onMovieClick = { movieId ->
                            navController.navigate(
                                    Route.FilmScreen().getRouteWithArgs(id = movieId)
                            ) { launchSingleTop = true }
                        },
                        onActorClick = {
                            // TODO: Implementar navegación a perfil de actor
                        }
                )
            }
        }
    }
}
