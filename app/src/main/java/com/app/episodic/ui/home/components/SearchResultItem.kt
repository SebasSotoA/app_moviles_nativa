package com.app.episodic.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.episodic.movie.domain.models.SearchItem
import com.app.episodic.utils.K
import com.app.episodic.utils.LanguageConstants

@Composable
fun SearchResultItem(
    item: SearchItem,
    modifier: Modifier = Modifier,
    onClick: (Int, String) -> Unit = { _, _ -> }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(item.mediaType) {
                detectTapGestures {
                    // Permitir click tanto para movies como para TV
                    onClick(item.id, item.mediaType)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        val imageUrl = item.posterPath?.let { K.BASE_IMAGE_URL + it }
        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = item.title,
            modifier = Modifier.size(width = 96.dp, height = 128.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            // Título más grande (2 líneas)
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            val year = item.year ?: "—"
            val duration = item.durationMinutes?.let { "$it Minutos" } ?: "—"
            val genre = item.genre ?: "—"
            val type = if (item.mediaType == "movie") "Película" else "Serie"
            val language = item.originalLanguage?.let { LanguageConstants.getLanguageNameByCode(it) } ?: "—"

            // Línea 1: Año
            Text(
                text = year,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Línea 2: Duración • Idioma
            Text(
                text = "$duration • $language",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Línea 3: Género | Tipo
            Text(
                text = "$genre | $type",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


