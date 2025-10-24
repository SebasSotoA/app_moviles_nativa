package com.app.episodic.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.episodic.ui.components.LoadingView
import com.app.episodic.ui.home.components.BodyContent
import com.app.episodic.ui.home.components.GenreButtons
import com.app.episodic.ui.home.components.HomeHeader
import com.app.episodic.ui.home.components.SearchResultItem
import com.app.episodic.ui.home.components.TopContent
import kotlinx.coroutines.delay

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit,
    onTvClick: (id: Int) -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onGenreSelected: (genreId: Int) -> Unit = {}
) {
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
    
    // Limpiar búsqueda cuando se navega a Home
    LaunchedEffect(Unit) {
        homeViewModel.clearSearch()
    }
    
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.discoverMovies.size }
    )
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    
    LaunchedEffect(key1 = pagerState.currentPage) {
        if (isDragged) {
            isAutoScrolling = false
        } else {
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < state.discoverMovies.size - 1) currentPage + 1 else 0
                scrollToPage(target)
            }
        }
    }
    
    Column(modifier = modifier.fillMaxSize()) {
        // Header with search bar and app title
        HomeHeader(
            searchText = searchText,
            onSearchTextChange = {
                searchText = it
                if (it.isEmpty()) {
                    homeViewModel.search("")
                }
            },
            onSearchClick = {
                homeViewModel.search(searchText)
            },
            onFilterClick = {
                // TODO: Implement filter functionality
            }
        )
        
        // Main content
        Box(modifier = Modifier.weight(1f)) {
            if (state.error != null) {
                Text(
                    text = state.error ?: "unknown error",
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 2,
                    modifier = Modifier.padding(16.dp)
                )
            }
            if (!state.isLoading && state.error == null) {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (state.isSearching) {
                        LazyColumn {
                            items(state.searchResults.size) { idx ->
                                SearchResultItem(
                                    item = state.searchResults[idx],
                                    onClick = { id, mediaType ->
                                        if (mediaType == "movie") {
                                            onMovieClick(id)
                                        } else if (mediaType == "tv") {
                                            onTvClick(id)
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        // Carrusel principal de películas
                        Box(modifier = Modifier.weight(0.4f)) {
                            HorizontalPager(
                                state = pagerState,
                                contentPadding = PaddingValues(defaultPadding),
                                pageSize = PageSize.Fill,
                                pageSpacing = itemSpacing
                            ) { page ->
                                if (isAutoScrolling) {
                                    AnimatedContent(
                                        targetState = page,
                                        label = "",
                                    ) { index ->
                                        TopContent(
                                            modifier = Modifier.fillMaxSize(),
                                            movie = state.discoverMovies[index],
                                            onMovieClick = onMovieClick
                                        )
                                    }
                                } else {
                                    TopContent(
                                        modifier = Modifier.fillMaxSize(),
                                        movie = state.discoverMovies[page],
                                        onMovieClick = onMovieClick
                                    )
                                }
                            }
                        }
                        
                        // Carrusel de géneros
                        GenreButtons(
                            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                            onGenreClick = { idString ->
                                val id = idString.toIntOrNull()
                                if (id != null) onGenreSelected(id)
                            }
                        )
                        
                        // Contenido principal
                        BodyContent(
                            modifier = Modifier.weight(0.6f),
                            discoverMovies = state.discoverMovies,
                            trendingMovies = state.trendingMovies,
                            discoverTvShows = state.discoverTvShows,
                            trendingTvShows = state.trendingTvShows,
                            onMovieClick = onMovieClick,
                            onTvClick = onTvClick
                        )
                    }
                }
            }
        }
    }
    LoadingView(isLoading = state.isLoading)
}