package com.app.episodic.ui.lists

import androidx.compose.runtime.Immutable

@Immutable
data class ListsState(
        val tab: ListsTab = ListsTab.FAVORITOS,
        val recentItems: List<ListItemUi> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
)

enum class ListsTab {
    FAVORITOS,
    LISTAS
}

@Immutable
data class ListItemUi(
        val id: Int,
        val title: String,
        val genre: String,
        val isMovie: Boolean,
        val posterUrl: String?,
        val rating: Double,
        val isFavorite: Boolean
)
