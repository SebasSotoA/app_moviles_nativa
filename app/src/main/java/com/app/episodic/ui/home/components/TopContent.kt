package com.app.episodic.ui.home.components

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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.ui.home.defaultPadding
import com.app.episodic.ui.home.itemSpacing
import com.app.episodic.utils.K
import com.app.episodic.utils.LanguageConstants
import com.app.episodic.R
import java.text.DecimalFormat


@Composable
fun TopContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (id: Int) -> Unit
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movie.posterPath}")
        .crossfade(true)
        .build()
    
    Card(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onMovieClick(movie.id) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            // Imagen de fondo (poster)
            AsyncImage(
                model = imgRequest,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                onError = {
                    it.result.throwable.printStackTrace()
                },
                placeholder = painterResource(id = R.drawable.bg_image_movie)
            )
            
            // Overlay semitransparente para mejorar legibilidad
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            
            // Información de la película distribuida por la card
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Fila superior: Título y Calificación
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Título de la película (izquierda)
                    val displayTitle = if (movie.title.isNotEmpty() && movie.title != "Unknown title") {
                        movie.title
                    } else {
                        movie.originalTitle
                    }
                    
                    Text(
                        text = displayTitle,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Calificación (derecha)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = DecimalFormat("#.#").format(movie.voteAverage),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Géneros (máximo 2)
                val displayGenres = movie.genreIds.take(2)
                if (displayGenres.isNotEmpty()) {
                    Text(
                        text = displayGenres.joinToString(" • "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Fecha de lanzamiento
                val releaseYear = movie.releaseDate.takeIf { 
                    it.isNotEmpty() && it != "Unknown date" 
                }?.take(4)
                if (releaseYear != null && releaseYear.isNotEmpty()) {
                    Text(
                        text = releaseYear,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Fila inferior: Idioma y Información adicional
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Idioma y información (izquierda)
                    val language = LanguageConstants.getLanguageNameByCode(movie.originalLanguage)
                    
                    // Información adicional: votos y popularidad
                    val additionalInfo = if (movie.voteCount > 1000) {
                        "${movie.voteCount / 1000}K votos"
                    } else {
                        "${movie.voteCount} votos"
                    }
                    
                    Text(
                        text = "$language • $additionalInfo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopContentPreview() {
    MaterialTheme {
        TopContent(
            movie = Movie(
                backdropPath = "",
                genreIds = listOf("Acción", "Aventura", "Fantasía"),
                id = 1,
                originalLanguage = "en",
                originalTitle = "Doctor Strange",
                overview = "A former neurosurgeon embarks on a journey of healing only to be drawn into the world of the mystic arts.",
                popularity = 8.5,
                posterPath = "/poster.jpg",
                releaseDate = "2016-10-26",
                title = "Doctor Strange",
                voteAverage = 7.4,
                voteCount = 1000,
                video = false
            ),
            onMovieClick = { }
        )
    }
}
