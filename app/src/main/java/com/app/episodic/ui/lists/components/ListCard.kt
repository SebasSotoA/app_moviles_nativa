package com.app.episodic.ui.lists.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.episodic.ui.lists.ListItemUi

@Composable
fun ListCard(
        item: ListItemUi,
        onClick: () -> Unit,
        onFavorite: () -> Unit,
        onMore: () -> Unit,
        modifier: Modifier = Modifier
) {
    Surface(
            modifier =
                    modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).clickable { onClick() },
            tonalElevation = 2.dp
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            if (item.posterUrl != null) {
                AsyncImage(
                        model = item.posterUrl,
                        contentDescription = "Poster de la lista",
                        modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                )
            } else {
                Box(
                        modifier =
                                Modifier.size(64.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.surfaceVariant),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                GenreChip(item.genre)
                Spacer(Modifier.height(4.dp))
                Text(
                        text = item.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                            text = if (item.isMovie) "Película" else "Serie",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(12.dp))
                    Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                    )
                    Text(
                            text = String.format("%.1f", item.rating),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onFavorite) {
                    Icon(
                            imageVector =
                                    if (item.isFavorite) Icons.Filled.Favorite
                                    else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint =
                                    if (item.isFavorite) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onMore) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Más opciones")
                }
            }
        }
    }
}

@Composable
private fun GenreChip(text: String) {
    Surface(
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.secondaryContainer,
            tonalElevation = 0.dp
    ) {
        Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ListCard_Preview() {
    val item =
            ListItemUi(
                    id = 1,
                    title = "Mi lista favorita de películas",
                    genre = "Acción",
                    isMovie = true,
                    posterUrl = null,
                    rating = 4.3,
                    isFavorite = true
            )
    ListCard(item = item, onClick = {}, onFavorite = {}, onMore = {})
}
