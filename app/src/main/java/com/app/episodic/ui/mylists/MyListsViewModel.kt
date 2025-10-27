package com.app.episodic.ui.mylists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.favorites.domain.models.FavoriteItem
import com.app.episodic.favorites.domain.repository.FavoritesRepository
import com.app.episodic.ui.mylists.components.MyListsTab
import com.app.episodic.utils.MovieGenreConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListsViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MyListsState())
    val state: StateFlow<MyListsState> = _state.asStateFlow()

    init {
        loadFavorites()
    }

    fun onTabSelected(tab: MyListsTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    fun onSortClick() {
        // TODO: Implementar lógica de ordenamiento
        _state.update { it.copy(showSortDialog = true) }
    }

    fun onFilterClick() {
        _state.update { it.copy(showFilterDialog = true) }
    }

    fun dismissSortDialog() {
        _state.update { it.copy(showSortDialog = false) }
    }

    fun dismissFilterDialog() {
        _state.update { it.copy(showFilterDialog = false) }
    }

    fun applyFilter(minRating: Float, genres: List<String>, year: Int) {
        // Guardar filtros y aplicarlos
        _state.update {
            it.copy(
                showFilterDialog = false,
                minRating = minRating,
                year = if (year <= 0) null else year,
                selectedGenres = genres
            )
        }

        applyFiltersToFavorites()
    }

    fun clearFilters() {
        _state.update {
            it.copy(
                minRating = 0f,
                selectedGenres = emptyList(),
                year = null
            )
        }
        // Restaurar la lista completa
        _state.update { it.copy(favorites = it.allFavorites) }
    }

    fun onFavoriteToggle(itemId: Int) {
        viewModelScope.launch {
            val favorite = favoritesRepository.getFavoriteById(itemId)
            if (favorite != null) {
                favoritesRepository.removeFromFavorites(itemId)
                loadFavorites() // Recargar la lista
            }
        }
    }

    fun onRemoveFromFavorites(itemId: Int) {
        viewModelScope.launch {
            favoritesRepository.removeFromFavorites(itemId)
            loadFavorites() // Recargar la lista
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            favoritesRepository.getAllFavorites().collect { favorites ->
                _state.update {
                    it.copy(
                        allFavorites = favorites,
                        favorites = favorites,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun applyFiltersToFavorites() {
        val current = _state.value
        val selectedGenreIds = current.selectedGenres.mapNotNull { MovieGenreConstants.getMovieGenreIdByName(it) }

        val filtered = current.allFavorites.filter { fav ->
            // Filtrar por rating
            val ratingOk = fav.voteAverage >= current.minRating
            // Filtrar por géneros: si no hay géneros seleccionados, pasar
            val genreOk = if (selectedGenreIds.isEmpty()) true else fav.genreIds.any { it in selectedGenreIds }
            // Filtrar por año si está presente
            val yearOk = current.year?.let { fav.releaseYear == it } ?: true
            ratingOk && genreOk && yearOk
        }

        _state.update { it.copy(favorites = filtered) }
    }

    fun refreshFavorites() {
        loadFavorites()
    }
}

data class MyListsState(
    val selectedTab: MyListsTab = MyListsTab.FAVORITOS,
    val allFavorites: List<FavoriteItem> = emptyList(),
    val favorites: List<FavoriteItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val showSortDialog: Boolean = false,
    val showFilterDialog: Boolean = false,
    val selectedGenres: List<String> = emptyList(),
    val minRating: Float = 0f,
    val year: Int? = null
)
