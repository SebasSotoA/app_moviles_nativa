package com.app.episodic.ui.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.episodic.R
import com.app.episodic.ui.explore.viewmodel.GenreViewModel

@Composable
fun GenreGrid(
    modifier: Modifier = Modifier,
    onGenreClick: (Int) -> Unit = {},
    genreViewModel: GenreViewModel = hiltViewModel()
) {
    val genreMovies by genreViewModel.genreMovies.collectAsState()
    val loadingGenres by genreViewModel.loadingGenres.collectAsState()
    
    // Lista completa de géneros con sus íconos
    val genres = listOf(
        GenreItem(28, stringResource(id = R.string.genre_action), getGenreIcon(28), null),
        GenreItem(12, stringResource(id = R.string.genre_adventure), getGenreIcon(12), null),
        GenreItem(16, stringResource(id = R.string.genre_animation), getGenreIcon(16), null),
        GenreItem(35, stringResource(id = R.string.genre_comedy), getGenreIcon(35), null),
        GenreItem(80, stringResource(id = R.string.genre_crime), getGenreIcon(80), null),
        GenreItem(99, stringResource(id = R.string.genre_documentary), getGenreIcon(99), null),
        GenreItem(18, stringResource(id = R.string.genre_drama), getGenreIcon(18), null),
        GenreItem(10751, stringResource(id = R.string.genre_family), getGenreIcon(10751), null),
        GenreItem(14, stringResource(id = R.string.genre_fantasy), getGenreIcon(14), null),
        GenreItem(36, stringResource(id = R.string.genre_history), getGenreIcon(36), null),
        GenreItem(27, stringResource(id = R.string.genre_horror), getGenreIcon(27), null),
        GenreItem(10402, stringResource(id = R.string.genre_music), getGenreIcon(10402), null),
        GenreItem(9648, stringResource(id = R.string.genre_mystery), getGenreIcon(9648), null),
        GenreItem(10749, stringResource(id = R.string.genre_romance), getGenreIcon(10749), null),
        GenreItem(878, stringResource(id = R.string.genre_scifi), getGenreIcon(878), null),
        GenreItem(10770, stringResource(id = R.string.genre_tv_movie), getGenreIcon(10770), null),
        GenreItem(53, stringResource(id = R.string.genre_thriller), getGenreIcon(53), null),
        GenreItem(10752, stringResource(id = R.string.genre_war), getGenreIcon(10752), null),
        GenreItem(37, stringResource(id = R.string.genre_western), getGenreIcon(37), null)
    )
    
    // Fetch movies for each genre
    LaunchedEffect(Unit) {
        genres.forEach { genre ->
            genreViewModel.fetchMovieForGenre(genre.id)
        }
    }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(genres) { genre ->
            val movie = genreMovies[genre.id]
            val isLoading = loadingGenres.contains(genre.id)
            val posterPath = movie?.posterPath
            
            GenreCard(
                genre = genre.copy(posterPath = posterPath),
                onClick = onGenreClick,
                isLoading = isLoading
            )
        }
    }
}

// Función para obtener el ícono correspondiente a cada género
fun getGenreIcon(genreId: Int): ImageVector {
    return when (genreId) {
        28 -> Icons.Default.LocalFireDepartment // Acción
        12 -> Icons.Default.Psychology // Aventura
        16 -> Icons.Default.ChildCare // Animación
        35 -> Icons.Default.EmojiEmotions // Comedia
        80 -> Icons.Default.Warning // Crimen
        99 -> Icons.Default.Search // Documental
        18 -> Icons.Default.Movie // Drama
        10751 -> Icons.Default.Home // Familiar
        14 -> Icons.Default.Star // Fantasía
        36 -> Icons.Default.HistoryEdu // Historia
        27 -> Icons.Default.Bolt // Terror
        10402 -> Icons.Default.MusicNote // Música
        9648 -> Icons.Default.Search // Misterio
        10749 -> Icons.Default.Favorite // Romance
        878 -> Icons.Default.Science // Ciencia ficción
        10770 -> Icons.Default.Tv // Película de TV
        53 -> Icons.Default.Warning // Suspenso
        10752 -> Icons.Default.Public // Guerra
        37 -> Icons.Default.Star // Oeste
        else -> Icons.Default.Movie
    }
}