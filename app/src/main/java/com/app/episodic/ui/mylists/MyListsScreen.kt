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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.episodic.custom_lists.presentation.viewmodel.CustomListsViewModel
import com.app.episodic.custom_lists.presentation.components.ListCard
import com.app.episodic.ui.home.components.FilterDialog
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
    val listsViewModel: CustomListsViewModel = hiltViewModel()
    val customLists by listsViewModel.lists.collectAsStateWithLifecycle()
    val listsLoading by listsViewModel.isLoading.collectAsStateWithLifecycle()

    // Refrescar al volver a la pantalla
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshFavorites()
                listsViewModel.loadLists()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Diálogo de filtros para favoritos
    if (state.showFilterDialog && state.selectedTab == MyListsTab.FAVORITOS) {
        FilterDialog(
            initialMinRating = state.minRating,
            initialSelectedGenres = state.selectedGenres,
            initialYear = state.selectedYear?.toString() ?: "",
            onDismiss = { viewModel.dismissFilterDialog() },
            onApplyFilter = { minRating, genres, year ->
                viewModel.applyFavoriteFilter(minRating, genres, year)
            },
            onClearFilters = { viewModel.clearFavoriteFilters() }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Listas", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            MyListsNavigationTabs(
                selectedTab = state.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )

            when (state.selectedTab) {
                MyListsTab.FAVORITOS -> {
                    Column {
                        MyListsContentHeader(
                            onSortClick = viewModel::onSortClick,
                            onFilterClick = viewModel::onFilterClick
                        )

                        if (state.isLoading) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        } else if (state.visibleFavorites.isEmpty()) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(
                                    text = "No hay favoritos con este filtro",
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(state.visibleFavorites) { favorite ->
                                    FavoriteCard(
                                        favoriteItem = favorite,
                                        onFavoriteToggle = viewModel::onFavoriteToggle,
                                        onInfoClick = { itemId ->
                                            if (favorite.isMovie) onMovieClick(itemId)
                                            else onTvClick(itemId)
                                        },
                                        onRemoveFromFavorites = viewModel::onRemoveFromFavorites
                                    )
                                }
                            }
                        }
                    }
                }

                MyListsTab.LISTAS -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
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
                                    modifier = Modifier.fillMaxWidth().padding(16.dp)
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

@Preview(showBackground = true)
@Composable
fun MyListsScreenPreview() {
    EpisodicTheme {
        MyListsScreen()
    }
}
