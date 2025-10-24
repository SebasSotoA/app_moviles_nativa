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
import androidx.compose.ui.res.stringResource
import com.app.episodic.R
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
        GenreButton("28", stringResource(id = R.string.genre_action)),
        GenreButton("18", stringResource(id = R.string.genre_drama)),
        GenreButton("35", stringResource(id = R.string.genre_comedy)),
        GenreButton("10749", stringResource(id = R.string.genre_romance)),
        GenreButton("27", stringResource(id = R.string.genre_horror)),
        GenreButton("878", stringResource(id = R.string.genre_scifi)),
        GenreButton("53", stringResource(id = R.string.genre_thriller)),
        GenreButton("16", stringResource(id = R.string.genre_animation)),
        GenreButton("12", stringResource(id = R.string.genre_adventure)),
        GenreButton("80", stringResource(id = R.string.genre_crime)),
        GenreButton("99", stringResource(id = R.string.genre_documentary)),
        GenreButton("14", stringResource(id = R.string.genre_fantasy)),
        GenreButton("36", stringResource(id = R.string.genre_history)),
        GenreButton("10402", stringResource(id = R.string.genre_music)),
        GenreButton("9648", stringResource(id = R.string.genre_mystery)),
        GenreButton("10751", stringResource(id = R.string.genre_family)),
        GenreButton("37", stringResource(id = R.string.genre_western))
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
