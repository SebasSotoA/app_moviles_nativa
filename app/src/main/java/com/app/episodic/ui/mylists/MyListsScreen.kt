package com.app.episodic.ui.mylists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.app.episodic.custom_lists.presentation.viewmodel.CustomListsViewModel
import com.app.episodic.custom_lists.presentation.components.ListCard
import com.app.episodic.ui.mylists.components.FavoriteCard
import com.app.episodic.ui.mylists.components.MyListsContentHeader
import com.app.episodic.ui.mylists.components.MyListsNavigationTabs
import com.app.episodic.ui.mylists.components.MyListsTab
import com.app.episodic.ui.theme.EpisodicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListsScreen(
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit = {},
    onTvClick: (Int) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCreateListClick: () -> Unit = {},
    onListClick: (String) -> Unit = {},
    viewModel: MyListsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // ViewModel para Listas personalizadas
    val listsViewModel: CustomListsViewModel = hiltViewModel()
    val customLists by listsViewModel.lists.collectAsStateWithLifecycle()
    val listsLoading by listsViewModel.isLoading.collectAsStateWithLifecycle()

    // Refrescar listas cuando se vuelve a esta pantalla (onResume)
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                listsViewModel.loadLists()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    
    LaunchedEffect(Unit) {
        viewModel.refreshFavorites()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Mis Listas",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Navegación con tabs
            MyListsNavigationTabs(
                selectedTab = state.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )
            
            // Contenido principal
            when (state.selectedTab) {
                MyListsTab.FAVORITOS -> {
                    Column {
                        // Header del contenido con "Actividad Reciente" y botones
                        MyListsContentHeader(
                            onSortClick = viewModel::onSortClick,
                            onFilterClick = viewModel::onFilterClick
                        )
                        
                        // Lista de favoritos
                        if (state.isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else if (state.favorites.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No has agregado nada aún aquí",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(state.favorites) { favorite ->
                                    FavoriteCard(
                                        favoriteItem = favorite,
                                        onFavoriteToggle = viewModel::onFavoriteToggle,
                                        onInfoClick = { itemId ->
                                            if (favorite.isMovie) {
                                                onMovieClick(itemId)
                                            } else {
                                                onTvClick(itemId)
                                            }
                                        },
                                        onRemoveFromFavorites = viewModel::onRemoveFromFavorites
                                    )
                                }
                            }
                        }
                    }
                }
                MyListsTab.LISTAS -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Listado de listas existentes
                            if (listsLoading) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) { CircularProgressIndicator() }
                            } else if (customLists.isEmpty()) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Crea una nueva lista",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                    )
                                }
                            } else {
                                LazyColumn(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(customLists) { list ->
                                        ListCard(
                                            customList = list,
                                            onListClick = { id -> onListClick(id) },
                                            onRenameClick = { listsViewModel.renameList(it, list.name) },
                                            onDeleteClick = { listsViewModel.deleteList(it) }
                                        )
                                    }
                                }
                            }

                            // Botón fijo Crear Lista
                            Button(
                                onClick = onCreateListClick,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                                    .height(56.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = androidx.compose.ui.graphics.Color(0xFF175e38),
                                    contentColor = androidx.compose.ui.graphics.Color(0xFFE8F5E8)
                                )
                            ) {
                                Text(
                                    text = "Crear Lista",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyListsScreenPreview() {
    EpisodicTheme {
        MyListsScreen()
    }
}