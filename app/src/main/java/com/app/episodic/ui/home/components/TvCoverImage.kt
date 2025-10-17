package com.app.episodic.ui.home.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.ui.home.itemSpacing
import com.app.episodic.utils.K


@Composable
fun TvCoverImage(
    modifier: Modifier = Modifier,
    tv: Tv,
    onTvClick:(Int) -> Unit
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${tv.posterPath}")
        .crossfade(true)
        .build()

    Box(
        modifier = modifier
            .size(width = 150.dp, height = 250.dp)
            .padding(itemSpacing)
            .pointerInput(tv.id) {
                detectTapGestures {
                    onTvClick(tv.id)
                }
            }
    ){
        AsyncImage(
            model = imgRequest,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(MaterialTheme.shapes.medium)
                .shadow(elevation = 4.dp),
            contentScale = ContentScale.Crop
        )
        MovieCard(
            shapes = CircleShape,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = "Marcador",
                modifier = Modifier.padding(4.dp)
            )
        }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.Black,
            contentColor = Color.White,
            shape = RoundedCornerShape(
                bottomEnd = 10.dp,
                bottomStart = 10.dp,
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                val displayTitle = if (tv.name.isNotEmpty() && tv.name != "Unknown title") {
                    tv.name
                } else {
                    tv.originalName
                }
                
                Text(
                    text = displayTitle, 
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    }

}
