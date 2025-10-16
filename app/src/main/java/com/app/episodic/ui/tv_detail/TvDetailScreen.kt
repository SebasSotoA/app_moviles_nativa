package com.app.episodic.ui.tv_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.episodic.ui.components.LoadingView
import com.app.episodic.ui.tv_detail.components.TvDetailBodyContent
import com.app.episodic.ui.tv_detail.components.TvDetailTopContent

@Composable
fun TvDetailScreen(
    tvId: Int,
    modifier: Modifier = Modifier,
    tvDetailViewModel: TvDetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onTvClick: (Int) -> Unit = {},
    onActorClick: (Int) -> Unit = {}
) {
    val state by tvDetailViewModel.tvDetailState.collectAsStateWithLifecycle()
    
    LaunchedEffect(tvId) {
        tvDetailViewModel.fetchTvDetail(tvId)
    }
    
    Box(modifier = modifier.fillMaxWidth()) {
        AnimatedVisibility(
            state.error != null,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                state.error ?: "Error desconocido",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
        AnimatedVisibility(visible = !state.isLoading && state.error == null) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boxHeight = maxHeight
                val topItemHeight = boxHeight * .4f
                val bodyItemHeight = boxHeight * .6f
                state.tvDetail?.let { tvDetail ->
                    TvDetailTopContent(
                        tvDetail = tvDetail,
                        modifier = Modifier
                            .height(topItemHeight)
                            .align(Alignment.TopCenter)
                    )
                    TvDetailBodyContent(
                        tvDetail = tvDetail,
                        tvShows = state.tvShows,
                        isTvLoading = state.isTvLoading,
                        fetchTvShows = tvDetailViewModel::fetchTvShows,
                        onTvClick = onTvClick,
                        onActorClick = onActorClick,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(bodyItemHeight)
                    )
                }
            }
        }
        IconButton(onClick = onNavigateUp, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Atrás")
        }
    }
    LoadingView(isLoading = state.isLoading)
}
