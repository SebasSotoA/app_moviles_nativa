package com.app.episodic.ui.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.utils.K
import com.app.episodic.utils.GenreConstants
import com.app.MovieApplication
import com.app.episodic.R

@Composable
fun ExploreItemCard(
    modifier: Modifier = Modifier,
    movie: Movie? = null,
    tv: Tv? = null,
    onClick: (Int) -> Unit = {}
) {
    val item = movie ?: tv
    val itemId = movie?.id ?: tv?.id ?: 0
    val title = movie?.title ?: tv?.name ?: ""
    val originalTitle = movie?.originalTitle ?: tv?.originalName ?: ""
    val posterPath = movie?.posterPath ?: tv?.posterPath ?: ""
    val releaseDate = movie?.releaseDate ?: tv?.firstAirDate ?: ""
    val genreIds = movie?.genreIds ?: tv?.genreIds ?: emptyList()
    
    // Obtener el primer género disponible
    val primaryGenre = when {
        genreIds.isNotEmpty() -> getGenreName(genreIds.first())
        else -> MovieApplication.appContext.getString(R.string.no_genre)
    }
    
    // Obtener el año de lanzamiento
    val year = try {
        releaseDate.take(4)
    } catch (e: Exception) {
        ""
    }
    
    Card(
        modifier = modifier
            .width(160.dp)
            .height(280.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick(itemId) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen de la película/serie
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                if (posterPath.isNotEmpty()) {
                    val imgRequest = ImageRequest.Builder(LocalContext.current)
                        .data("${K.BASE_IMAGE_URL}$posterPath")
                        .crossfade(true)
                        .build()
                    
                    AsyncImage(
                        model = imgRequest,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Placeholder cuando no hay imagen
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Información de la película/serie
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Título en la parte superior
                Text(
                    text = title.ifEmpty { originalTitle },
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                // Género y año en la parte inferior
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Movie,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = primaryGenre,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = "|",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Text(
                        text = year,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Función auxiliar para obtener el nombre del género
private fun getGenreName(genreId: String): String {
    val id = genreId.toIntOrNull() ?: -1
    if (id <= 0) return MovieApplication.appContext.getString(R.string.other)
    return GenreConstants.getGenreNameById(id)
}

@Preview(showBackground = true)
@Composable
fun ExploreItemCardPreview() {
    MaterialTheme {
        ExploreItemCard(
            movie = Movie(
                id = 1,
                title = "Interestelar",
                originalTitle = "Interstellar",
                overview = "",
                posterPath = "",
                backdropPath = "",
                genreIds = listOf("878", "18"),
                releaseDate = "2014-11-05",
                voteAverage = 8.5,
                voteCount = 1000,
                popularity = 100.0,
                originalLanguage = "en",
                video = false
            )
        )
    }
}
