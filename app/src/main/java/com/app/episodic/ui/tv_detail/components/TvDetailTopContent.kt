package com.app.episodic.ui.tv_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.episodic.R
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.ui.home.components.MovieCard
import com.app.episodic.ui.home.defaultPadding
import com.app.episodic.ui.home.itemSpacing
import com.app.episodic.ui.theme.primaryLightHighContrast
import com.app.episodic.utils.K

@Composable
fun TvDetailTopContent(
    modifier: Modifier = Modifier,
    tvDetail: TvDetail
) {
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
        TvDetailComponent(
            rating = tvDetail.voteAverage,
            firstAirDate = tvDetail.firstAirDate,
            numberOfSeasons = tvDetail.numberOfSeasons,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = defaultPadding)
        ) {
            Card(
                onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp),
            ) {
                Row(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "reproducir")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Mirar Ahora")
                }
            }
            Card(
                onClick = { /*TODO*/ },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = primaryLightHighContrast
                ),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp)
            ) {
                Row(
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Tv, contentDescription = "trailer")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Ver Trailer")
                }
            }
        }
    }
}


