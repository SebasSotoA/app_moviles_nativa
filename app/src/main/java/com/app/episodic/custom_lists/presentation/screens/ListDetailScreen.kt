package com.app.episodic.custom_lists.presentation.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.res.stringResource
import com.app.episodic.R
import com.app.episodic.custom_lists.presentation.viewmodel.CustomListsViewModel
import com.app.episodic.utils.K

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    listId: String,
    onBack: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onTvClick: (Int) -> Unit,
    viewModel: CustomListsViewModel = hiltViewModel()
) {
    val lists by viewModel.lists.collectAsStateWithLifecycle()
    val currentList = lists.find { it.id == listId }
    val items = currentList?.items ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = currentList?.name ?: stringResource(id = R.string.list_label),
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stringResource(id = R.string.elements_count, items.size),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.nav_back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        if (items.isEmpty()) {
                Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.empty_list),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 8.dp)
            ) {
                items(items) { it ->
                    ListDetailItem(
                        id = it.id,
                        title = it.title,
                        posterPath = it.posterPath,
                        isMovie = it.isMovie,
                        onInfo = { id, isMovie -> if (isMovie) onMovieClick(id) else onTvClick(id) },
                        onRemove = { id -> viewModel.removeItemFromList(listId, id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ListDetailItem(
    id: Int,
    title: String,
    posterPath: String,
    isMovie: Boolean,
    onInfo: (Int, Boolean) -> Unit,
    onRemove: (Int) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        val imageUrl = K.BASE_IMAGE_URL + posterPath
        androidx.compose.foundation.Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = title,
            modifier = Modifier
                .pointerInput(id) { detectTapGestures { onInfo(id, isMovie) } }
                .size(width = 96.dp, height = 128.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Spacer(modifier = androidx.compose.ui.Modifier.size(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = androidx.compose.ui.Modifier.height(6.dp))
            Text(
                text = if (isMovie) stringResource(id = R.string.type_movie) else stringResource(id = R.string.type_series),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = { showMenu = true }) {
            Icon(imageVector = Icons.Filled.MoreVert, contentDescription = stringResource(id = R.string.options))
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.information)) },
                onClick = {
                    showMenu = false
                    onInfo(id, isMovie)
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.delete)) },
                onClick = {
                    showMenu = false
                    onRemove(id)
                }
            )
        }
    }
}


