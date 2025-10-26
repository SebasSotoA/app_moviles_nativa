package com.app.episodic.ui.mylists.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.episodic.favorites.domain.models.FavoriteItem
import com.app.episodic.utils.GenreConstants
import androidx.compose.ui.res.stringResource
import com.app.episodic.R
import com.app.episodic.utils.K

@Composable
fun FavoriteCard(
    modifier: Modifier = Modifier,
    favoriteItem: FavoriteItem,
    onFavoriteToggle: (Int) -> Unit = {},
    onInfoClick: (Int) -> Unit = {},
    onRemoveFromFavorites: (Int) -> Unit = {}
) {
    var showMenu by remember { mutableStateOf(false) }
    var isFavorite by remember { mutableStateOf(true) }

    val genreName = if (favoriteItem.genreIds.isNotEmpty()) {
        GenreConstants.getGenreNameById(favoriteItem.genreIds.first())
    } else {
        stringResource(id = R.string.no_genre)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen de la película/serie
            Box(
                modifier = Modifier
                    .size(width = 80.dp, height = 120.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                val imgRequest = ImageRequest.Builder(LocalContext.current)
                    .data("${K.BASE_IMAGE_URL}${favoriteItem.posterPath}")
                    .crossfade(true)
                    .build()

                AsyncImage(
                    model = imgRequest,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información de la película/serie
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // Género
                Text(
                    text = genreName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Título
                Text(
                    text = favoriteItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Tipo, año y calificación
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tipo
                    Text(
                        text = if (favoriteItem.isMovie) stringResource(id = R.string.type_movie)
                        else stringResource(id = R.string.type_series),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Año
                    favoriteItem.releaseYear?.let { year ->
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = year.toString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Estrella y calificación
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = String.format("%.1f", favoriteItem.voteAverage),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Botones de acción
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Botón favorito
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                        onFavoriteToggle(favoriteItem.id)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = stringResource(id = R.string.favorite),
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Menú opciones
                Box {
                    IconButton(
                        onClick = { showMenu = true }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.more_options),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.remove_from_favorites)) },
                            onClick = {
                                showMenu = false
                                onRemoveFromFavorites(favoriteItem.id)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.information)) },
                            onClick = {
                                showMenu = false
                                onInfoClick(favoriteItem.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
