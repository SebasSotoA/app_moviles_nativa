package com.app.episodic.ui.tv_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv_detail.domain.models.Cast
import com.app.episodic.tv_detail.domain.models.Review
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.ui.tv_detail.components.TvActorItem
import com.app.episodic.ui.home.components.MovieCard
import com.app.episodic.ui.home.components.MovieCoverImage
import com.app.episodic.ui.home.defaultPadding
import com.app.episodic.ui.home.itemSpacing

@Composable
fun TvDetailBodyContent(
    modifier: Modifier = Modifier,
    tvDetail: TvDetail,
    tvShows: List<Tv>,
    isTvLoading: Boolean,
    fetchTvShows: () -> Unit,
    onTvClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
) {
    LazyColumn(modifier) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            tvDetail.genreIds.forEachIndexed { index, genreText ->
                                Text(
                                    text = genreText,
                                    modifier = Modifier
                                        .padding(6.dp),
                                    maxLines = 1,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                // Show divider after all except the last item
                                if (index < tvDetail.genreIds.lastIndex) {
                                    Text(
                                        text = " • ",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        Text(
                            text = tvDetail.runTime,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = tvDetail.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    
                    // Información específica de TV
                    TvSpecificInfo(tvDetail = tvDetail)
                    Spacer(modifier = Modifier.height(itemSpacing))
                    
                    Text(
                        text = tvDetail.overview,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        ActionIcon.entries.forEachIndexed { index, actionIcon ->
                            ActionIconBtn(
                                icon = actionIcon.icon,
                                contentDescription = actionIcon.contentDescription,
                                bgColor = if (index == ActionIcon.entries.lastIndex)
                                    MaterialTheme.colorScheme.primaryContainer
                                else Color.Black.copy(
                                    .5f
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = itemSpacing),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Reparto y Equipo",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "Reparto y Equipo"
                            )
                        }
                    }
                    LazyRow {
                        items(tvDetail.cast) { cast ->
                            TvActorItem(
                                cast = cast,
                                modifier = Modifier
                                    .pointerInput(cast.id) {
                                        detectTapGestures {
                                            onActorClick(cast.id)
                                        }
                                    }
                            )
                            Spacer(modifier = Modifier.width(defaultPadding))
                        }
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))

                    TvInfoItem(
                        infoItem = tvDetail.language,
                        title = "Idiomas",
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    TvInfoItem(
                        infoItem = tvDetail.productionCountry,
                        title = "Países de Producción",
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = "Reseñas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Review(reviews = tvDetail.reviews)
                    Spacer(modifier = Modifier.height(itemSpacing))
                    MoreLikeThisTv(
                        fetchTvShows = fetchTvShows,
                        isTvLoading = isTvLoading,
                        tvShows = tvShows,
                        onTvClick = onTvClick
                    )
                }
            }
        }
    }
}

@Composable
private fun TvSpecificInfo(tvDetail: TvDetail) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Temporadas: ${tvDetail.numberOfSeasons}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Episodios: ${tvDetail.numberOfEpisodes}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Column {
            Text(
                text = "Estado: ${tvDetail.status}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tipo: ${tvDetail.type}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
    
    // Próximo episodio si está disponible
    tvDetail.nextEpisodeToAir?.let { nextEpisode ->
        Spacer(modifier = Modifier.height(itemSpacing))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(defaultPadding)
            ) {
                Text(
                    text = "Próximo Episodio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "T${nextEpisode.seasonNumber}E${nextEpisode.episodeNumber}: ${nextEpisode.name}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Fecha: ${nextEpisode.airDate}",
                    style = MaterialTheme.typography.bodySmall
                )
                if (nextEpisode.overview.isNotEmpty()) {
                    Text(
                        text = nextEpisode.overview,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun MoreLikeThisTv(
    modifier: Modifier = Modifier,
    fetchTvShows: () -> Unit,
    isTvLoading: Boolean,
    tvShows: List<Tv>,
    onTvClick: (Int) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        fetchTvShows()
    }
    Column(modifier) {
        Text(
            text = "Más como esto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        LazyRow {
            item {
                AnimatedVisibility(visible = isTvLoading) {
                    CircularProgressIndicator()
                }
            }
            items(tvShows) { tvShow ->
                // TODO: Crear TvCoverImage similar a MovieCoverImage
                Text(
                    text = tvShow.name,
                    modifier = Modifier.pointerInput(tvShow.id) {
                        detectTapGestures {
                            onTvClick(tvShow.id)
                        }
                    }
                )
            }
        }
    }
}

private enum class ActionIcon(val icon: ImageVector, val contentDescription: String) {
    BookMark(icon = Icons.Default.Bookmark, "marcador"),
    Share(icon = Icons.Default.Share, "compartir"),
    Download(icon = Icons.Default.Download, "descargar"),
}

@Composable
private fun ActionIconBtn(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    bgColor: Color = Color.Black.copy(.8f)
) {
    MovieCard(
        shapes = CircleShape,
        modifier = modifier
            .padding(4.dp),
        bgColor = bgColor
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
private fun TvInfoItem(infoItem: List<String>, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        infoItem.forEach {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun Review(
    modifier: Modifier = Modifier,
    reviews: List<Review>
) {
    val (viewMore, setViewMore) = remember {
        mutableStateOf(false)
    }
    // show only three reviews or less by default
    val defaultReview =
        if (reviews.size > 3) reviews.take(3) else reviews
    // show more when user needs more review
    val tvReviews = if (viewMore) reviews else defaultReview
    val btnText = if (viewMore) "Colapsar" else "Más..."
    Column(modifier) {
        tvReviews.forEach { review ->
            ReviewItem(review = review)
            Spacer(modifier = Modifier.height(itemSpacing))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(itemSpacing))
        }
        TextButton(onClick = { setViewMore(!viewMore) }) {
            Text(text = btnText)
        }
    }
}

@Composable
private fun ReviewItem(review: Review) {
    Column {
        Text(
            text = review.author,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = review.content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


