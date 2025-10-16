package com.app.episodic.ui.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.episodic.ui.lists.components.EmptyListsView
import com.app.episodic.ui.lists.components.ListCard
import com.app.episodic.ui.lists.components.ListsTabs
import com.app.episodic.ui.lists.components.ListsTopBar
import com.app.episodic.ui.lists.components.RecentActivityHeader
import com.app.episodic.ui.theme.EpisodicTheme

object ListsScreenLabels {
    const val SCREEN_TITLE = "Mis Listas"
    const val TAB_FAVORITOS = "Favoritos"
    const val TAB_LISTAS = "Listas"
    const val RECENT_ACTIVITY = "Actividad Reciente"
    const val EMPTY_TITLE = "Todavía no tienes ninguna lista."
    const val EMPTY_SUBTITLE = "¡Hagamos una!"
    const val BUTTON_CREATE_LIST = "Crear una lista"
}

@Composable
fun ListsScreen(
        viewModel: ListsViewModel = hiltViewModel(),
        onOpenSearch: () -> Unit = {},
        onOpenDetail: (id: Int) -> Unit = {},
        onCreateList: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()

    EpisodicTheme {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            ListsTopBar(title = ListsScreenLabels.SCREEN_TITLE, onSearch = onOpenSearch)
            ListsTabs(selectedTab = state.tab, onTabSelected = viewModel::onChangeTab)
            RecentActivityHeader(onSort = { /*TODO*/}, onFilter = { /*TODO*/})
            Spacer(Modifier.height(12.dp))
            if (state.recentItems.isEmpty()) {
                EmptyListsView(
                        title = ListsScreenLabels.EMPTY_TITLE,
                        subtitle = ListsScreenLabels.EMPTY_SUBTITLE,
                        onCreateList = {
                            viewModel.onCreateListClick()
                            onCreateList()
                        }
                )
            } else {
                LazyColumn(
                        Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.recentItems.size) { idx ->
                        val item = state.recentItems[idx]
                        ListCard(
                                item = item,
                                onClick = { onOpenDetail(item.id) },
                                onFavorite = { viewModel.onToggleFavorite(item.id) },
                                onMore = { viewModel.onMoreClicked(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListsScreen_EmptyPreview() {
    ListsScreenPreview(state = ListsState())
}

@Preview(showBackground = true)
@Composable
fun ListsScreen_NonEmptyPreview() {
    val mockState =
            ListsState(
                    tab = ListsTab.FAVORITOS,
                    recentItems =
                            listOf(
                                    ListItemUi(
                                            1,
                                            "Lista Suspenso",
                                            "Thriller",
                                            true,
                                            null,
                                            4.0,
                                            false
                                    ),
                                    ListItemUi(2, "Series Largas", "Drama", false, null, 4.2, true)
                            ),
                    isLoading = false,
                    error = null
            )
    ListsScreenPreview(state = mockState)
}

@Composable
private fun ListsScreenPreview(state: ListsState) {
    EpisodicTheme {
        Column(Modifier.fillMaxSize()) {
            ListsTopBar(ListsScreenLabels.SCREEN_TITLE) {}
            ListsTabs(selectedTab = state.tab, onTabSelected = {})
            RecentActivityHeader(onSort = {}, onFilter = {})
            Spacer(Modifier.height(12.dp))
            if (state.recentItems.isEmpty()) {
                EmptyListsView(
                        title = ListsScreenLabels.EMPTY_TITLE,
                        subtitle = ListsScreenLabels.EMPTY_SUBTITLE,
                        onCreateList = {}
                )
            } else {
                LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.recentItems.size) { idx ->
                        val item = state.recentItems[idx]
                        ListCard(item = item, onClick = {}, onFavorite = {}, onMore = {})
                    }
                }
            }
        }
    }
}
