package com.app.episodic.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie.domain.models.SearchItem
import com.app.episodic.movie.domain.repository.MovieRepository
import com.app.episodic.movie.domain.repository.SearchRepository
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv.domain.repository.TvRepository
import com.app.episodic.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val tvRepository: TvRepository,
    private val searchRepository: SearchRepository,
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    init {
        fetchDiscoverMovie()
        fetchTrendingMovie()
        fetchDiscoverTvShows()
        fetchTrendingTvShows()
    }


    private fun fetchDiscoverMovie() = viewModelScope.launch {
        repository.fetchDiscoverMovie().collectAndHandle(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { movie ->
            _homeState.update {
                it.copy(isLoading = false, error = null, discoverMovies = movie)
            }
        }
    }
    private fun fetchTrendingMovie() = viewModelScope.launch {
        repository.fetchTrendingMovie().collectAndHandle(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { movie ->
            _homeState.update {
                it.copy(isLoading = false, error = null, trendingMovies = movie)
            }
        }
    }

    private fun fetchDiscoverTvShows() = viewModelScope.launch {
        tvRepository.fetchDiscoverTv().collectAndHandle(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { tvShows ->
            _homeState.update {
                it.copy(isLoading = false, error = null, discoverTvShows = tvShows)
            }
        }
    }

    private fun fetchTrendingTvShows() = viewModelScope.launch {
        tvRepository.fetchTrendingTv().collectAndHandle(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { tvShows ->
            _homeState.update {
                it.copy(isLoading = false, error = null, trendingTvShows = tvShows)
            }
        }
    }

    fun search(query: String) = viewModelScope.launch {
        if (query.isBlank()) {
            _homeState.update { it.copy(searchResults = emptyList(), isSearching = false) }
            return@launch
        }
        searchRepository.search(query).collectAndHandle(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.message, isSearching = false)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null, isSearching = true)
                }
            }
        ) { results ->
            _homeState.update {
                it.copy(isLoading = false, error = null, searchResults = results, isSearching = true)
            }
        }
    }

    fun clearSearch() {
        _homeState.update { 
            it.copy(searchResults = emptyList(), isSearching = false) 
        }
    }

    fun onFilterClick() {
        _homeState.update { it.copy(showFilterDialog = true) }
    }

    fun dismissFilterDialog() {
        _homeState.update { it.copy(showFilterDialog = false) }
    }

    fun applyFilter(minRating: Float = 0f, selectedGenres: List<String> = emptyList()) {
        _homeState.update { 
            it.copy(
                showFilterDialog = false,
                minRating = minRating,
                selectedGenres = selectedGenres
            )
        }
    }

    fun clearFilters() {
        _homeState.update { 
            it.copy(
                minRating = 0f,
                selectedGenres = emptyList()
            )
        }
    }

}

data class HomeState(
    val discoverMovies: List<Movie> = emptyList(),
    val trendingMovies: List<Movie> = emptyList(),
    val discoverTvShows: List<Tv> = emptyList(),
    val trendingTvShows: List<Tv> = emptyList(),
    val searchResults: List<SearchItem> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false,
    val showFilterDialog: Boolean = false,
    val minRating: Float = 0f,
    val selectedGenres: List<String> = emptyList()
)