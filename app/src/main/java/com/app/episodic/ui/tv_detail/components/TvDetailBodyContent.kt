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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv_detail.domain.models.Cast
import com.app.episodic.tv_detail.domain.models.Review
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.ui.tv_detail.components.TvActorItem
import com.app.episodic.ui.home.components.MovieCard
import com.app.episodic.ui.home.components.MovieCoverImage
import com.app.episodic.ui.home.components.TvCoverImage
import com.app.episodic.ui.home.defaultPadding
import com.app.episodic.ui.home.itemSpacing
import com.app.episodic.ui.components.CollapsibleText
import kotlin.math.round
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TvDetailBodyContent(
    modifier: Modifier = Modifier,
    tvDetail: TvDetail,
    tvShows: List<Tv>,
    isTvLoading: Boolean,
    fetchTvShows: () -> Unit,
    onTvClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
    onAddToListClick: () -> Unit,
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
                            val shownGenres = tvDetail.genreIds.take(2)
                            shownGenres.forEachIndexed { index, genreText ->
                                Text(
                                    text = genreText,
                                    modifier = Modifier
                                        .padding(6.dp),
                                    maxLines = 1,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                // Show divider after all except the last item
                                if (index < shownGenres.lastIndex) {
                                    Text(
                                        text = " • ",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                        Text(
                            text = tvDetail.runTime,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
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
                    // Botón de Mi Lista
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            onClick = onAddToListClick,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(30.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF175e38),
                                contentColor = Color(0xFFE8F5E8)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "Agregar a Mi Lista",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color(0xFFE8F5E8)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "+ Mi lista",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE8F5E8)
                                )
                            }
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

                    Spacer(modifier = Modifier.height(16.dp))
                    TvInfoItem(
                        infoItem = tvDetail.language,
                        title = "Idiomas",
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    TvProductionCountriesItem(
                        countries = tvDetail.productionCountry,
                        title = "Países de producción",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF175e38).copy(alpha = 0.9f),
                contentColor = Color(0xFFE8F5E8)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(defaultPadding)
            ) {
                Text(
                    text = "Próximo Episodio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE8F5E8)
                )
                Text(
                    text = "T${nextEpisode.seasonNumber}E${nextEpisode.episodeNumber}: ${nextEpisode.name}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFE8F5E8)
                )
                Text(
                    text = "Fecha: ${nextEpisode.airDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFE8F5E8).copy(alpha = 0.8f)
                )
                if (nextEpisode.overview.isNotEmpty()) {
                    Text(
                        text = nextEpisode.overview,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFE8F5E8).copy(alpha = 0.9f)
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
                TvCoverImage(tv = tvShow, onTvClick = onTvClick)
            }
        }
    }
}


@Composable
private fun TvInfoItem(infoItem: List<String>, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            infoItem.forEachIndexed { index, item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (index < infoItem.size - 1) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TvProductionCountriesItem(countries: List<String>, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            countries.forEachIndexed { index, country ->
                Text(
                    text = translateCountryToSpanish(country),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (index < countries.size - 1) {
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

private fun translateCountryToSpanish(country: String): String {
    return when (country.lowercase()) {
        "united states of america", "usa", "us" -> "Estados Unidos"
        "united kingdom", "uk" -> "Reino Unido"
        "canada" -> "Canadá"
        "france" -> "Francia"
        "germany" -> "Alemania"
        "spain" -> "España"
        "italy" -> "Italia"
        "japan" -> "Japón"
        "china" -> "China"
        "australia" -> "Australia"
        "brazil" -> "Brasil"
        "mexico" -> "México"
        "argentina" -> "Argentina"
        "india" -> "India"
        "south korea" -> "Corea del Sur"
        "russia" -> "Rusia"
        "netherlands" -> "Países Bajos"
        "sweden" -> "Suecia"
        "norway" -> "Noruega"
        "denmark" -> "Dinamarca"
        "finland" -> "Finlandia"
        "poland" -> "Polonia"
        "czech republic" -> "República Checa"
        "hungary" -> "Hungría"
        "romania" -> "Rumania"
        "bulgaria" -> "Bulgaria"
        "greece" -> "Grecia"
        "turkey" -> "Turquía"
        "israel" -> "Israel"
        "south africa" -> "Sudáfrica"
        "egypt" -> "Egipto"
        "morocco" -> "Marruecos"
        "tunisia" -> "Túnez"
        "algeria" -> "Argelia"
        "libya" -> "Libia"
        "sudan" -> "Sudán"
        "ethiopia" -> "Etiopía"
        "kenya" -> "Kenia"
        "nigeria" -> "Nigeria"
        "ghana" -> "Ghana"
        "senegal" -> "Senegal"
        "ivory coast" -> "Costa de Marfil"
        "cameroon" -> "Camerún"
        "congo" -> "Congo"
        "uganda" -> "Uganda"
        "tanzania" -> "Tanzania"
        "zimbabwe" -> "Zimbabue"
        "botswana" -> "Botsuana"
        "namibia" -> "Namibia"
        "zambia" -> "Zambia"
        "malawi" -> "Malaui"
        "mozambique" -> "Mozambique"
        "madagascar" -> "Madagascar"
        "mauritius" -> "Mauricio"
        "seychelles" -> "Seychelles"
        "comoros" -> "Comoras"
        "djibouti" -> "Yibuti"
        "somalia" -> "Somalia"
        "eritrea" -> "Eritrea"
        "chad" -> "Chad"
        "niger" -> "Níger"
        "mali" -> "Malí"
        "burkina faso" -> "Burkina Faso"
        "guinea" -> "Guinea"
        "sierra leone" -> "Sierra Leona"
        "liberia" -> "Liberia"
        "gambia" -> "Gambia"
        "guinea-bissau" -> "Guinea-Bisáu"
        "cape verde" -> "Cabo Verde"
        "são tomé and príncipe" -> "Santo Tomé y Príncipe"
        "equatorial guinea" -> "Guinea Ecuatorial"
        "gabon" -> "Gabón"
        "central african republic" -> "República Centroafricana"
        "democratic republic of the congo" -> "República Democrática del Congo"
        "angola" -> "Angola"
        "burundi" -> "Burundi"
        "rwanda" -> "Ruanda"
        "lesotho" -> "Lesoto"
        "swaziland" -> "Suazilandia"
        "south sudan" -> "Sudán del Sur"
        else -> country // Si no se encuentra traducción, mantener el nombre original
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
    
    Column(modifier) {
        if (reviews.isEmpty()) {
            Text(
                text = "No hay reseñas disponibles",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(2.dp)
            )
        } else {
            // show only three reviews or less by default
            val defaultReview =
                if (reviews.size > 3) reviews.take(3) else reviews
            // show more when user needs more review
            val tvReviews = if (viewMore) reviews else defaultReview
            val btnText = if (viewMore) "Colapsar" else "Más..."
            
            tvReviews.forEach { review ->
                ReviewItem(review = review)
                Spacer(modifier = Modifier.height(itemSpacing))
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(itemSpacing))
            }
            
            if (reviews.size > 3) {
                TextButton(onClick = { setViewMore(!viewMore) }) {
                    Text(text = btnText)
                }
            }
        }
    }
}

@Composable
private fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review
) {
    Column (modifier){
        val nameAnnotatedString = buildAnnotatedString {
            append(review.author)
            append(" • ")
            append(formatDate(review.createdAt))
        }
        val ratingAnnotatedString = buildAnnotatedString {
            // Apply bold style to rating
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(round(review.rating).toString()) // round to nearest int for display
            pop() // End bold styling

            // Apply small font size to "10"
            pushStyle(SpanStyle(fontSize = 10.sp))
            append("10")
            pop() // End small styling
        }
        Text(
            text = nameAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(itemSpacing))
        CollapsibleText(text = review.content, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(itemSpacing))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
            Text(text = ratingAnnotatedString, style = MaterialTheme.typography.bodySmall)
        }
    }
}


private fun formatDate(dateString: String): String {
    return try {
        // Parse the input date (assuming it's in ISO format like "2023-12-25T10:30:00.000Z")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        // If parsing fails, try to extract just the date part
        if (dateString.contains("T")) {
            dateString.substring(0, dateString.indexOf("T"))
        } else {
            dateString
        }
    }
}
