package com.app.episodic.ui.mylists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.favorites.domain.models.FavoriteItem
import com.app.episodic.favorites.domain.repository.FavoritesRepository
import com.app.episodic.ui.mylists.components.MyListsTab
import com.app.episodic.utils.MovieGenreConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var favoritesCollectorJob: Job? = null

    init {
        loadFavorites()
    }

    fun onTabSelected(tab: MyListsTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    fun onSortClick() {
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

    fun onFavoriteToggle(itemId: Int) {
        viewModelScope.launch {
            val favorite = favoritesRepository.getFavoriteById(itemId)
            if (favorite != null) {
                favoritesRepository.removeFromFavorites(itemId)
                // Actualizar UI de forma optimista sin volver a crear collectors
                _state.update { state ->
                    val newFavorites = state.favorites.filterNot { it.id == itemId }
                    val newVisible = state.visibleFavorites.filterNot { it.id == itemId }
                    state.copy(favorites = newFavorites, visibleFavorites = newVisible)
                }
                // Asegurar que la lista se refresque desde el repositorio si este no emite cambios
                loadFavorites()
            }
        }
    }

    fun onRemoveFromFavorites(itemId: Int) {
        viewModelScope.launch {
            favoritesRepository.removeFromFavorites(itemId)
            // Actualizar UI de forma optimista sin volver a crear collectors
            _state.update { state ->
                val newFavorites = state.favorites.filterNot { it.id == itemId }
                val newVisible = state.visibleFavorites.filterNot { it.id == itemId }
                state.copy(favorites = newFavorites, visibleFavorites = newVisible)
            }
            // Forzar recarga por si el repositorio no notifica cambios
            loadFavorites()
        }
    }

    fun applyFavoriteFilter(
        minRating: Float = 0f,
        genres: List<String> = emptyList(),
        year: Int? = null
    ) {
        _state.update { state ->
            val genreIds = genres.mapNotNull { MovieGenreConstants.getMovieGenreIdByName(it) }

            val filtered = state.favorites.filter { item ->
                val ratingOk = item.voteAverage >= minRating
                val genreOk = genreIds.isEmpty() || item.genreIds.any { it in genreIds }
                val yearOk = year == null || item.releaseYear == year
                ratingOk && genreOk && yearOk
            }

            state.copy(
                showFilterDialog = false,
                minRating = minRating,
                selectedGenres = genres,
                selectedYear = year,
                visibleFavorites = filtered
            )
        }
    }

    fun clearFavoriteFilters() {
        _state.update { state ->
            state.copy(
                minRating = 0f,
                selectedGenres = emptyList(),
                selectedYear = null,
                visibleFavorites = state.favorites
            )
        }
    }

    private fun loadFavorites() {
        // Cancelar collector previo si existe
        favoritesCollectorJob?.cancel()
        favoritesCollectorJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            favoritesRepository.getAllFavorites().collect { favorites ->
                // Aplicar filtros activos al recibir la lista desde el repositorio
                _state.update { state ->
                    val genreIds = state.selectedGenres.mapNotNull { MovieGenreConstants.getMovieGenreIdByName(it) }

                    val filtered = favorites.filter { item ->
                        val ratingOk = item.voteAverage >= state.minRating
                        val genreOk = genreIds.isEmpty() || item.genreIds.any { it in genreIds }
                        val yearOk = state.selectedYear == null || item.releaseYear == state.selectedYear
                        ratingOk && genreOk && yearOk
                    }

                    state.copy(
                        favorites = favorites,
                        visibleFavorites = filtered,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun refreshFavorites() {
        loadFavorites()
    }
}

data class MyListsState(
    val selectedTab: MyListsTab = MyListsTab.FAVORITOS,
    val favorites: List<FavoriteItem> = emptyList(),
    val visibleFavorites: List<FavoriteItem> = emptyList(),
    val minRating: Float = 0f,
    val selectedGenres: List<String> = emptyList(),
    val selectedYear: Int? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val showSortDialog: Boolean = false,
    val showFilterDialog: Boolean = false
)
