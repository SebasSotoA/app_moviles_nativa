package com.app.episodic.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie.domain.repository.MovieRepository
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv.domain.repository.TvRepository
import com.app.episodic.ui.explore.components.ExploreTab
import com.app.episodic.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) : ViewModel() {
    
    private val _exploreState = MutableStateFlow(ExploreState())
    val exploreState = _exploreState.asStateFlow()
    
    init {
        loadPopularMovies()
    }
    
    fun onTabSelected(tab: ExploreTab) {
        _exploreState.update { it.copy(selectedTab = tab) }
        
        when (tab) {
            ExploreTab.PELICULAS -> {
                if (_exploreState.value.popularMovies.isEmpty()) {
                    loadPopularMovies()
                }
            }
            ExploreTab.SERIES -> {
                if (_exploreState.value.popularTvShows.isEmpty()) {
                    loadPopularTvShows()
                }
            }
            ExploreTab.GENEROS -> {
                // TODO: Implementar carga de géneros
            }
        }
    }
    
    private fun loadPopularMovies() = viewModelScope.launch {
        movieRepository.fetchDiscoverMovie().collectAndHandle(
            onError = { error ->
                _exploreState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _exploreState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { movies ->
            _exploreState.update {
                it.copy(
                    isLoading = false,
                    error = null,
                    popularMovies = movies
                )
            }
        }
    }
    
    private fun loadPopularTvShows() = viewModelScope.launch {
        tvRepository.fetchDiscoverTv().collectAndHandle(
            onError = { error ->
                _exploreState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _exploreState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { tvShows ->
            _exploreState.update {
                it.copy(
                    isLoading = false,
                    error = null,
                    popularTvShows = tvShows
                )
            }
        }
    }
    
    fun onSortClick() {
        // TODO: Implementar lógica de ordenamiento
        _exploreState.update { it.copy(showSortDialog = true) }
    }
    
    fun onFilterClick() {
        // TODO: Implementar lógica de filtrado
        _exploreState.update { it.copy(showFilterDialog = true) }
    }
    
    fun dismissSortDialog() {
        _exploreState.update { it.copy(showSortDialog = false) }
    }
    
    fun dismissFilterDialog() {
        _exploreState.update { it.copy(showFilterDialog = false) }
    }
}

data class ExploreState(
    val selectedTab: ExploreTab = ExploreTab.PELICULAS,
    val popularMovies: List<Movie> = emptyList(),
    val popularTvShows: List<Tv> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showSortDialog: Boolean = false,
    val showFilterDialog: Boolean = false
)
