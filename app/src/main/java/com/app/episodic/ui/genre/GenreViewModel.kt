package com.app.episodic.ui.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie.domain.repository.MovieRepository
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv.domain.repository.TvRepository
import com.app.episodic.utils.TvGenreConstants
import com.app.episodic.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TvRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GenreState())
    val state: StateFlow<GenreState> = _state.asStateFlow()

    fun loadGenreContent(genreId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            // Cargar películas del género
            movieRepository.fetchMoviesByGenre(genreId).collectAndHandle(
                onError = { error ->
                    _state.value = _state.value.copy(
                        error = error?.message,
                        isLoadingMovies = false
                    )
                },
                onLoading = {
                    _state.value = _state.value.copy(isLoadingMovies = true)
                },
                stateReducer = { movies ->
                    _state.value = _state.value.copy(
                        movies = movies,
                        isLoadingMovies = false
                    )
                }
            )
            
            // Cargar series del género equivalente
            val tvGenreId = TvGenreConstants.getEquivalentTvGenreId(genreId)
            if (tvGenreId != null) {
                tvRepository.fetchTvByGenre(tvGenreId).collectAndHandle(
                    onError = { error ->
                        _state.value = _state.value.copy(
                            error = error?.message,
                            isLoadingTv = false
                        )
                    },
                    onLoading = {
                        _state.value = _state.value.copy(isLoadingTv = true)
                    },
                    stateReducer = { tvShows ->
                        _state.value = _state.value.copy(
                            tvShows = tvShows,
                            isLoadingTv = false
                        )
                    }
                )
            } else {
                // Si no hay equivalente de TV, establecer lista vacía
                _state.value = _state.value.copy(
                    tvShows = emptyList(),
                    isLoadingTv = false
                )
            }
            
            _state.value = _state.value.copy(isLoading = false)
        }
    }
}

data class GenreState(
    val movies: List<Movie> = emptyList(),
    val tvShows: List<Tv> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMovies: Boolean = false,
    val isLoadingTv: Boolean = false,
    val error: String? = null
)
