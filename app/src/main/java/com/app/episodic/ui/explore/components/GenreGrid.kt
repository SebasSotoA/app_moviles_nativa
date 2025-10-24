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
    // Lista de géneros principales con sus íconos y posters
    val genres = listOf(
        GenreItem(28, stringResource(id = R.string.genre_action), getGenreIcon(28), getGenrePosterPath(28)),
        GenreItem(27, stringResource(id = R.string.genre_horror), getGenreIcon(27), getGenrePosterPath(27)),
        GenreItem(878, stringResource(id = R.string.genre_scifi), getGenreIcon(878), getGenrePosterPath(878)),
        GenreItem(10749, stringResource(id = R.string.genre_romance), getGenreIcon(10749), getGenrePosterPath(10749)),
        GenreItem(35, stringResource(id = R.string.genre_comedy), getGenreIcon(35), getGenrePosterPath(35)),
        GenreItem(12, stringResource(id = R.string.genre_adventure), getGenreIcon(12), getGenrePosterPath(12)),
        GenreItem(14, stringResource(id = R.string.genre_fantasy), getGenreIcon(14), getGenrePosterPath(14)),
        GenreItem(18, stringResource(id = R.string.genre_drama), getGenreIcon(18), getGenrePosterPath(18))
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
        27 -> Icons.Default.Bolt // Terror
        878 -> Icons.Default.Science // Ciencia ficción
        10749 -> Icons.Default.Favorite // Romance
        35 -> Icons.Default.EmojiEmotions // Comedia
        12 -> Icons.Default.Psychology // Aventura
        14 -> Icons.Default.Movie // Fantasía
        18 -> Icons.Default.Sports // Drama (usando Sports como placeholder)
        else -> Icons.Default.Movie
    }
}

// Función para obtener un poster de ejemplo por género
fun getGenrePosterPath(genreId: Int): String? {
    return when (genreId) {
        28 -> "/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg" // Acción - Avatar
        27 -> "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg" // Terror - IT (2017)
        878 -> "/rSPw7tgCH9c6NqICZef4kZjFOQ5.jpg" // Ciencia ficción - Blade Runner 2049
        10749 -> "/6XYLiMxHAaCsoyrVo38LBWMw2p8.jpg" // Romance - Titanic  
        35 -> "/xmbU4JTUm8rsdtn7Y3Fcm30GpeT.jpg" // Comedia - Barbie
        12 -> "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg" // Aventura - Jurassic Park
        14 -> "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg" // Fantasía - Harry Potter
        18 -> "/lmZFxXgJE3vgrciwuDib0N8CfQo.jpg" // Drama - The Shawshank Redemption
        else -> null
    }
}
