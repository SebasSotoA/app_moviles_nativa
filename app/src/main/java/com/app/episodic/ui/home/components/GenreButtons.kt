package com.app.episodic.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class GenreButton(
    val id: String,
    val name: String
)

@Composable
fun GenreButtons(
    modifier: Modifier = Modifier,
    onGenreClick: (genreId: String) -> Unit = {}
) {
    val genres = listOf(
        GenreButton("28", "Acción"),
        GenreButton("18", "Drama"),
        GenreButton("35", "Comedia"),
        GenreButton("10749", "Romance"),
        GenreButton("27", "Terror"),
        GenreButton("878", "Ciencia Ficción"),
        GenreButton("53", "Suspenso"),
        GenreButton("16", "Animación"),
        GenreButton("12", "Aventura"),
        GenreButton("80", "Crimen"),
        GenreButton("99", "Documental"),
        GenreButton("14", "Fantasía"),
        GenreButton("36", "Historia"),
        GenreButton("10402", "Música"),
        GenreButton("9648", "Misterio"),
        GenreButton("10751", "Familia"),
        GenreButton("37", "Western")
    )

    var selectedGenre by remember { mutableStateOf<String?>(null) } // Sin selección por defecto

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            GenreButton(
                genre = genre,
                isSelected = selectedGenre == genre.id,
                onClick = {
                    selectedGenre = if (selectedGenre == genre.id) null else genre.id
                    onGenreClick(genre.id)
                }
            )
        }
    }
}

@Composable
private fun GenreButton(
    modifier: Modifier = Modifier,
    genre: GenreButton,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(0xFF9E9E9E) // Gris claro para seleccionado
    } else {
        Color(0xFF616161) // Gris oscuro para no seleccionado
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = genre.name,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreButtonsPreview() {
    MaterialTheme {
        GenreButtons()
    }
}
