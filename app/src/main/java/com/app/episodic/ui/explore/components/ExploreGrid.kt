package com.app.episodic.ui.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.tv.domain.models.Tv

@Composable
fun ExploreGrid(
    modifier: Modifier = Modifier,
    movies: List<Movie> = emptyList(),
    tvShows: List<Tv> = emptyList(),
    onMovieClick: (Int) -> Unit = {},
    onTvClick: (Int) -> Unit = {},
    minRating: Float = 0f
) {
    // Filter lists based on rating
    val filteredMovies = if (minRating > 0f) {
        movies.filter { it.voteAverage >= minRating }
    } else {
        movies
    }
    
    val filteredTvShows = if (minRating > 0f) {
        tvShows.filter { it.voteAverage >= minRating }
    } else {
        tvShows
    }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mostrar películas
        items(filteredMovies) { movie ->
            ExploreItemCard(
                movie = movie,
                onClick = onMovieClick
            )
        }
        
        // Mostrar series de TV
        items(filteredTvShows) { tv ->
            ExploreItemCard(
                tv = tv,
                onClick = onTvClick
            )
        }
    }
}

@Composable
fun ExploreGridWithMixedContent(
    modifier: Modifier = Modifier,
    items: List<ExploreItem> = emptyList(),
    onMovieClick: (Int) -> Unit = {},
    onTvClick: (Int) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            when (item) {
                is ExploreItem.Movie -> ExploreItemCard(
                    movie = item.movie,
                    onClick = onMovieClick
                )
                is ExploreItem.Tv -> ExploreItemCard(
                    tv = item.tv,
                    onClick = onTvClick
                )
            }
        }
    }
}

sealed class ExploreItem {
    data class Movie(val movie: com.app.episodic.movie.domain.models.Movie) : ExploreItem()
    data class Tv(val tv: com.app.episodic.tv.domain.models.Tv) : ExploreItem()
}
