package com.app.episodic.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.ui.home.itemSpacing

@Composable
fun BodyContent(
    modifier: Modifier = Modifier,
    discoverMovies: List<Movie>,
    trendingMovies: List<Movie>,
    discoverTvShows: List<Tv>,
    trendingTvShows: List<Tv>,
    onMovieClick: (id: Int) -> Unit,
    onTvClick: (id: Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Descubrir Películas",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Descubre nuevas películas"
                        )
                    }
                }
                LazyRow {
                    items(discoverMovies) {
                        MovieCoverImage(
                            movie = it,
                            onMovieClick = onMovieClick,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "En Tendencia",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Trending now"
                        )
                    }
                }
                LazyRow {
                    items(trendingMovies) {
                        MovieCoverImage(
                            movie = it,
                            onMovieClick = onMovieClick,
                        )
                    }
                }
                
                // Sección de Series - Descubrir
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Descubrir Series",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Descubre nuevas series"
                        )
                    }
                }
                LazyRow {
                    items(discoverTvShows) {
                        TvCoverImage(
                            tv = it,
                            onTvClick = onTvClick,
                        )
                    }
                }
                
                // Sección de Series - En Tendencia
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Series en Tendencia",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Series en tendencia"
                        )
                    }
                }
                LazyRow {
                    items(trendingTvShows) {
                        TvCoverImage(
                            tv = it,
                            onTvClick = onTvClick,
                        )
                    }
                }
            }

        }

    }

}