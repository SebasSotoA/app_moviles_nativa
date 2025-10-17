package com.app.episodic.ui.tv_detail.components

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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.widget.Toast
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.episodic.favorites.domain.models.FavoriteItem
import com.app.episodic.favorites.presentation.viewmodel.FavoritesViewModel
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.ui.home.components.MovieCard
import com.app.episodic.ui.home.defaultPadding
import com.app.episodic.ui.home.itemSpacing
import com.app.episodic.ui.theme.primaryLightHighContrast
import com.app.episodic.utils.K
import com.app.episodic.utils.GenreConstants
import com.app.episodic.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TvDetailTopContent(
    modifier: Modifier = Modifier,
    tvDetail: TvDetail,
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val isFavorite by favoritesViewModel.isFavorite.collectAsStateWithLifecycle()
    val toastMessage by favoritesViewModel.toastMessage.collectAsStateWithLifecycle()
    val isTvFavorite = isFavorite[tvDetail.id] ?: false
    val context = LocalContext.current
    
    LaunchedEffect(tvDetail.id) {
        favoritesViewModel.checkIfFavorite(tvDetail.id)
    }
    
    LaunchedEffect(toastMessage) {
        toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            favoritesViewModel.clearToastMessage()
        }
    }
    
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${tvDetail.posterPath}")
        .crossfade(true)
        .build()
    Box(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = imgRequest,
            contentDescription = null, // decorative element
            modifier = Modifier
                .matchParentSize(),
            contentScale = ContentScale.Crop,
            onError = {
                it.result.throwable.printStackTrace()
            },
            placeholder = painterResource(id = R.drawable.bg_image_movie)
        )
        
        // Botón de favorito en la esquina superior derecha
        IconButton(
            onClick = {
                val favoriteItem = FavoriteItem(
                    id = tvDetail.id,
                    title = tvDetail.name,
                    originalTitle = tvDetail.originalName,
                    posterPath = tvDetail.posterPath,
                    genreIds = tvDetail.genreIds.map { genreName ->
                        // Convertir nombre de género a ID usando GenreConstants
                        GenreConstants.getGenreIdByName(genreName) ?: 0
                    }.filter { it != 0 },
                    voteAverage = tvDetail.voteAverage,
                    isMovie = false
                )
                favoritesViewModel.toggleFavorite(favoriteItem)
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (isTvFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = if (isTvFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                tint = if (isTvFavorite) Color.Red else Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        
        TvDetailComponent(
            rating = tvDetail.voteAverage,
            firstAirDate = tvDetail.firstAirDate,
            numberOfSeasons = tvDetail.numberOfSeasons,
            reviewCount = tvDetail.reviews.size,
            modifier = Modifier
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
private fun TvDetailComponent(
    modifier: Modifier = Modifier,
    rating: Double,
    firstAirDate: String,
    numberOfSeasons: Int,
    reviewCount: Int,
) {
    Column(modifier) {
        MovieCard(
            modifier = Modifier.padding(horizontal = defaultPadding)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Calificación",
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "%.1f".format(rating))
                }
                Spacer(modifier = Modifier.width(itemSpacing))
                VerticalDivider(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.width(itemSpacing))
                Text(
                    text = firstAirDate,
                    modifier = Modifier
                        .padding(6.dp),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(itemSpacing))
                VerticalDivider(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.width(itemSpacing))
                Text(
                    text = "$numberOfSeasons Temporada${if (numberOfSeasons != 1) "s" else ""}",
                    modifier = Modifier
                        .padding(6.dp),
                    maxLines = 1
                )
            }
        }
        
        // Mostrar cantidad de reviews
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = defaultPadding, vertical = 8.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF175e38).copy(alpha = 0.85f),
                contentColor = Color(0xFFE8F5E8)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$reviewCount reseña(s)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFE8F5E8)
                )
            }
        }
    }
}


