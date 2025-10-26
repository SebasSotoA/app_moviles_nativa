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
import androidx.compose.ui.res.stringResource
import com.app.episodic.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun GenreGrid(
    modifier: Modifier = Modifier,
    onGenreClick: (Int) -> Unit = {}
) {
    // Lista completa de géneros con sus íconos y posters
    val genres = listOf(
        GenreItem(28, stringResource(id = R.string.genre_action), getGenreIcon(28), getGenrePosterPath(28)),
        GenreItem(12, stringResource(id = R.string.genre_adventure), getGenreIcon(12), getGenrePosterPath(12)),
        GenreItem(16, stringResource(id = R.string.genre_animation), getGenreIcon(16), getGenrePosterPath(16)),
        GenreItem(35, stringResource(id = R.string.genre_comedy), getGenreIcon(35), getGenrePosterPath(35)),
        GenreItem(80, stringResource(id = R.string.genre_crime), getGenreIcon(80), getGenrePosterPath(80)),
        GenreItem(99, stringResource(id = R.string.genre_documentary), getGenreIcon(99), getGenrePosterPath(99)),
        GenreItem(18, stringResource(id = R.string.genre_drama), getGenreIcon(18), getGenrePosterPath(18)),
        GenreItem(10751, stringResource(id = R.string.genre_family), getGenreIcon(10751), getGenrePosterPath(10751)),
        GenreItem(14, stringResource(id = R.string.genre_fantasy), getGenreIcon(14), getGenrePosterPath(14)),
        GenreItem(36, stringResource(id = R.string.genre_history), getGenreIcon(36), getGenrePosterPath(36)),
        GenreItem(27, stringResource(id = R.string.genre_horror), getGenreIcon(27), getGenrePosterPath(27)),
        GenreItem(10402, stringResource(id = R.string.genre_music), getGenreIcon(10402), getGenrePosterPath(10402)),
        GenreItem(9648, stringResource(id = R.string.genre_mystery), getGenreIcon(9648), getGenrePosterPath(9648)),
        GenreItem(10749, stringResource(id = R.string.genre_romance), getGenreIcon(10749), getGenrePosterPath(10749)),
        GenreItem(878, stringResource(id = R.string.genre_scifi), getGenreIcon(878), getGenrePosterPath(878)),
        GenreItem(10770, stringResource(id = R.string.genre_tv_movie), getGenreIcon(10770), getGenrePosterPath(10770)),
        GenreItem(53, stringResource(id = R.string.genre_thriller), getGenreIcon(53), getGenrePosterPath(53)),
        GenreItem(10752, stringResource(id = R.string.genre_war), getGenreIcon(10752), getGenrePosterPath(10752)),
        GenreItem(37, stringResource(id = R.string.genre_western), getGenreIcon(37), getGenrePosterPath(37))
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(genres) { genre ->
            GenreCard(
                genre = genre,
                onClick = onGenreClick
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

// Función para obtener un poster de ejemplo por género
fun getGenrePosterPath(genreId: Int): String? {
    return when (genreId) {
        28 -> "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg" // Acción - Avatar
        12 -> "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg" // Aventura - Jurassic Park
        16 -> "/qNBAXBIQlnOThrVvA6mA2B5ggV6.jpg" // Animación - Toy Story
        35 -> "/xmbU4JTUm8rsdtn7Y3Fcm30GpeT.jpg" // Comedia - Barbie
        80 -> "/qJ2tW6WMUDux911r6m7haRef0WH.jpg" // Crimen - The Godfather
        99 -> "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg" // Documental - Free Solo
        18 -> "/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg" // Drama - The Shawshank Redemption
        10751 -> "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg" // Familiar - Harry Potter
        14 -> "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg" // Fantasía - Harry Potter
        36 -> "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg" // Historia - Free Solo
        27 -> "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg" // Terror - IT (2017)
        10402 -> "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg" // Música - Free Solo
        9648 -> "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg" // Misterio - IT (2017)
        10749 -> "/6XYLiMxHAaCsoyrVo38LBWMw2p8.jpg" // Romance - Titanic
        878 -> "/rSPw7tgCH9c6NqICZef4kZjFOQ5.jpg" // Ciencia ficción - Blade Runner 2049
        10770 -> "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg" // Película de TV - Avatar
        53 -> "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg" // Suspenso - IT (2017)
        10752 -> "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg" // Guerra - Free Solo
        37 -> "/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg" // Oeste - Free Solo
        else -> null
    }
}
