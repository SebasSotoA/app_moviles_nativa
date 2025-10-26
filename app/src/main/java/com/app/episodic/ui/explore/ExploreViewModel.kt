package com.app.episodic.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie.domain.repository.MovieRepository
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv.domain.repository.TvRepository
import com.app.episodic.ui.explore.components.ExploreTab
import com.app.episodic.utils.MovieGenreConstants
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
        loadPopularTvShows()
    }

    fun onTabSelected(tab: ExploreTab) {
        _exploreState.update { it.copy(selectedTab = tab) }
    }

    fun onSortClick() {
        _exploreState.update { it.copy(showSortDialog = true) }
    }

    fun onFilterClick() {
        _exploreState.update { it.copy(showFilterDialog = true) }
    }

    fun dismissFilterDialog() {
        _exploreState.update { it.copy(showFilterDialog = false) }
    }

    fun dismissSortDialog() {
        _exploreState.update { it.copy(showSortDialog = false) }
    }

    fun applyFilter(minRating: Float, genres: List<String>, year: Int?) {
        _exploreState.update {
            it.copy(
                showFilterDialog = false,
                minRating = minRating,
                year = year,
                selectedGenres = genres
            )
        }

        when (_exploreState.value.selectedTab) {
            ExploreTab.PELICULAS -> applyFiltersForMovies()
            ExploreTab.SERIES -> applyFiltersForTvShows()
            else -> Unit
        }
    }

    fun clearFilters() {
        _exploreState.update {
            it.copy(
                minRating = 0f,
                selectedGenres = emptyList(),
                year = null
            )
        }
        loadPopularMovies()
        loadPopularTvShows()
    }

    private fun loadPopularMovies() = viewModelScope.launch {
        movieRepository.fetchDiscoverMovie().collectAndHandle(
            onError = { error -> _exploreState.update { it.copy(isLoading = false, error = error?.message) } },
            onLoading = { _exploreState.update { it.copy(isLoading = true, error = null) } }
        ) { movies ->
            _exploreState.update {
                it.copy(
                    allPopularMovies = movies,
                    visibleMovies = movies,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    private fun loadPopularTvShows() = viewModelScope.launch {
        tvRepository.fetchDiscoverTv().collectAndHandle(
            onError = { error -> _exploreState.update { it.copy(isLoading = false, error = error?.message) } },
            onLoading = { _exploreState.update { it.copy(isLoading = true, error = null) } }
        ) { tvShows ->
            _exploreState.update {
                it.copy(
                    allPopularTvShows = tvShows,
                    visibleTvShows = tvShows,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    private fun applyFiltersForMovies() {
        val state = _exploreState.value
        val genreIds = state.selectedGenres.mapNotNull { MovieGenreConstants.getMovieGenreIdByName(it) }

        viewModelScope.launch {
            movieRepository.fetchFilteredMovies(
                genres = genreIds,
                minRating = state.minRating,
                year = state.year
            ).collectAndHandle(
                onError = { error -> _exploreState.update { it.copy(isLoading = false, error = error?.message) } },
                onLoading = { _exploreState.update { it.copy(isLoading = true, error = null) } }
            ) { movies ->
                _exploreState.update {
                    it.copy(
                        visibleMovies = movies,
                        allPopularMovies = movies,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }

    private fun applyFiltersForTvShows() {
        val state = _exploreState.value
        val genreIds = state.selectedGenres.mapNotNull { MovieGenreConstants.getMovieGenreIdByName(it) }

        viewModelScope.launch {
            tvRepository.fetchFilteredTvShows(
                genres = genreIds,
                minRating = state.minRating,
                year = state.year
            ).collectAndHandle(
                onError = { error -> _exploreState.update { it.copy(isLoading = false, error = error?.message) } },
                onLoading = { _exploreState.update { it.copy(isLoading = true, error = null) } }
            ) { tvShows ->
                _exploreState.update {
                    it.copy(
                        visibleTvShows = tvShows,
                        allPopularTvShows = tvShows,
                        isLoading = false,
                        error = null
                    )
                }
            }
        }
    }
}

data class ExploreState(
    val selectedTab: ExploreTab = ExploreTab.PELICULAS,
    val allPopularMovies: List<Movie> = emptyList(),
    val allPopularTvShows: List<Tv> = emptyList(),
    val visibleMovies: List<Movie> = emptyList(),
    val visibleTvShows: List<Tv> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showSortDialog: Boolean = false,
    val showFilterDialog: Boolean = false,
    val selectedGenres: List<String> = emptyList(),
    val minRating: Float = 0f,
    val year: Int? = null
)
