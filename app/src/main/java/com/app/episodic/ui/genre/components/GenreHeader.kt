package com.app.episodic.ui.genre.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.episodic.utils.GenreConstants

@Composable
fun GenreHeader(
    modifier: Modifier = Modifier,
    genreId: Int
) {
    val genreName = GenreConstants.getGenreNameById(genreId)
    val genreIcon = getGenreIcon(genreId)
    val genreTagline = getGenreTagline(genreId)
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Ícono del género
        Icon(
            imageVector = genreIcon,
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        // Nombre del género
        Text(
            text = genreName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        
        // Frase del género
        Text(
            text = genreTagline,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

// Función para obtener el ícono del género
fun getGenreIcon(genreId: Int): ImageVector {
    return when (genreId) {
        28 -> Icons.Default.LocalFireDepartment // Acción
        27 -> Icons.Default.Bolt // Terror
        878 -> Icons.Default.Science // Ciencia ficción
        10749 -> Icons.Default.Favorite // Romance
        35 -> Icons.Default.EmojiEmotions // Comedia
        12 -> Icons.Default.Psychology // Aventura
        14 -> Icons.Default.Movie // Fantasía
        18 -> Icons.Default.Sports // Drama
        else -> Icons.Default.Movie
    }
}

// Función para obtener la frase del género
fun getGenreTagline(genreId: Int): String {
    return when (genreId) {
        28 -> "¡No te pierdas la acción ni un momento!" // Acción
        27 -> "¡Prepárate para los sustos más intensos!" // Terror
        878 -> "¡Explora los límites del futuro!" // Ciencia ficción
        10749 -> "¡Déjate llevar por el amor!" // Romance
        35 -> "¡Ríe hasta que te duela la panza!" // Comedia
        12 -> "¡Vive aventuras épicas!" // Aventura
        14 -> "¡Sumérgete en mundos mágicos!" // Fantasía
        18 -> "¡Historias que tocan el corazón!" // Drama
        16 -> "¡Diversión para toda la familia!" // Animación
        80 -> "¡Misterios que te mantendrán en vilo!" // Crimen
        99 -> "¡Descubre la realidad!" // Documental
        10751 -> "¡Entretenimiento familiar!" // Familiar
        36 -> "¡Revive momentos históricos!" // Historia
        10402 -> "¡Déjate llevar por la música!" // Música
        9648 -> "¡Misterios que resolver!" // Misterio
        10770 -> "¡Series que no te puedes perder!" // Película de TV
        53 -> "¡Suspenso que te mantiene alerta!" // Suspenso
        10752 -> "¡Batallas épicas te esperan!" // Guerra
        37 -> "¡Aventuras del lejano oeste!" // Oeste
        else -> "¡Descubre contenido increíble!"
    }
}
