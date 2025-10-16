package com.app.episodic.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.episodic.ui.components.LoadingView
import com.app.episodic.ui.home.components.BodyContent
import com.app.episodic.ui.home.components.HomeHeader
import com.app.episodic.ui.home.components.TopContent
import kotlinx.coroutines.delay

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit
) {
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }
    var searchText by remember {
        mutableStateOf("")
    }
    
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
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
            onSearchTextChange = { searchText = it },
            onSearchClick = {
                // TODO: Implement search functionality
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
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val boxHeight = maxHeight
                    val topItemHeight = boxHeight * .35f
                    val bodyItemHeight = boxHeight * .65f
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
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .heightIn(min = topItemHeight),
                                    movie = state.discoverMovies[index],
                                    onMovieClick = onMovieClick
                                )
                            }
                        } else {
                            TopContent(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .heightIn(min = topItemHeight),
                                movie = state.discoverMovies[page],
                                onMovieClick = onMovieClick
                            )
                        }
                    }
                    BodyContent(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .heightIn(max = bodyItemHeight),
                        discoverMovies = state.discoverMovies,
                        trendingMovies = state.trendingMovies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
    LoadingView(isLoading = state.isLoading)
}