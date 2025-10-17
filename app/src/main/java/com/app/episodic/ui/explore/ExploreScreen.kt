package com.app.episodic.ui.explore

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.episodic.ui.components.LoadingView
import com.app.episodic.ui.explore.components.ExploreContentHeader
import com.app.episodic.ui.explore.components.ExploreGrid
import com.app.episodic.ui.explore.components.ExploreHeader
import com.app.episodic.ui.explore.components.ExploreNavigationTabs
import com.app.episodic.ui.explore.components.ExploreTab
import com.app.episodic.ui.theme.EpisodicTheme

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    exploreViewModel: ExploreViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit = {},
    onTvClick: (Int) -> Unit = {},
    onSearchClick: () -> Unit = {}
) {
    val state by exploreViewModel.exploreState.collectAsStateWithLifecycle()
    
    Column(modifier = modifier.fillMaxSize()) {
        // Header con título y lupa de búsqueda
        ExploreHeader(
            onSearchClick = onSearchClick
        )
        
        // Navegación con tabs
        ExploreNavigationTabs(
            selectedTab = state.selectedTab,
            onTabSelected = exploreViewModel::onTabSelected
        )
        
        // Contenido principal
        Box(modifier = Modifier.weight(1f)) {
            when {
                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error ?: "Error desconocido",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                !state.isLoading -> {
                    Column {
                        // Header del contenido con "Popular" y botones
                        ExploreContentHeader(
                            onSortClick = exploreViewModel::onSortClick,
                            onFilterClick = exploreViewModel::onFilterClick
                        )
                        
                        // Grid de contenido
                        when (state.selectedTab) {
                            ExploreTab.PELICULAS -> {
                                ExploreGrid(
                                    movies = state.popularMovies,
                                    onMovieClick = onMovieClick
                                )
                            }
                            ExploreTab.SERIES -> {
                                ExploreGrid(
                                    tvShows = state.popularTvShows,
                                    onTvClick = onTvClick
                                )
                            }
                            ExploreTab.GENEROS -> {
                                // TODO: Implementar vista de géneros
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Géneros próximamente",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Loading view
        LoadingView(isLoading = state.isLoading)
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreScreenPreview() {
    EpisodicTheme {
        ExploreScreen()
    }
}
