package com.app.episodic.ui.detail.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie_detail.domain.models.MovieDetail
import com.app.episodic.movie_detail.domain.models.Review
import com.app.episodic.ui.home.components.MovieCard
import com.app.episodic.ui.home.components.MovieCoverImage
import com.app.episodic.ui.home.defaultPadding
import com.app.episodic.ui.home.itemSpacing


@Composable
fun DetailBodyContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    movies: List<Movie>,
    isMovieLoading: Boolean,
    fetchMovies: () -> Unit,
    onMovieClick: (Int) -> Unit,
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
                            val shownGenres = movieDetail.genreIds.take(2)
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
                            text = movieDetail.runTime,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = movieDetail.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = movieDetail.overview,
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
                                contentDescription = "Cast & Crew"
                            )
                        }
                    }
                    LazyRow {
                        items(movieDetail.cast) { cast ->
                            ActorItem(
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
                    MovieInfoItem(
                        infoItem = movieDetail.language,
                        title = "Idiomas",
                    )
                    
                    ProductionCountriesItem(
                        countries = movieDetail.productionCountry,
                        title = "Países de producción",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = "Reseñas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Review(reviews = movieDetail.reviews)
                    Spacer(modifier = Modifier.height(itemSpacing))
                    MoreLikeThis(
                        fetchMovies = fetchMovies,
                        isMovieLoading = isMovieLoading,
                        movies = movies,
                        onMovieClick = onMovieClick
                    )

                }
            }
        }


    }

}


@Composable
fun MoreLikeThis(
    modifier: Modifier = Modifier,
    fetchMovies: () -> Unit,
    isMovieLoading: Boolean,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        fetchMovies()
    }
    Column(modifier) {
        Text(
            text = "Más como esto",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        LazyRow {
            item {
                AnimatedVisibility(visible = isMovieLoading) {
                    CircularProgressIndicator()
                }
            }
            items(movies) {
                MovieCoverImage(movie = it, onMovieClick = onMovieClick)
            }
        }
    }

}


@Composable
private fun ProductionCountriesItem(countries: List<String>, title: String) {
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
            verticalAlignment = Alignment.CenterVertically
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
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieInfoItem(infoItem: List<String>, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
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
            val movieReviews = if (viewMore) reviews else defaultReview
            val btnText = if (viewMore) "Colapsar" else "Más..."
            
            movieReviews.forEach { review ->
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
