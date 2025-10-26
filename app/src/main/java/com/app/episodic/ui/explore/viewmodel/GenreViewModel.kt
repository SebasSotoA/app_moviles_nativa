package com.app.episodic.ui.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie.domain.repository.MovieRepository
import com.app.episodic.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    
    private val _genreMovies = MutableStateFlow<Map<Int, Movie?>>(emptyMap())
    val genreMovies: StateFlow<Map<Int, Movie?>> = _genreMovies.asStateFlow()
    
    private val _loadingGenres = MutableStateFlow<Set<Int>>(emptySet())
    val loadingGenres: StateFlow<Set<Int>> = _loadingGenres.asStateFlow()
    
    fun fetchMovieForGenre(genreId: Int) {
        // Check if we already have a movie for this genre
        if (_genreMovies.value.containsKey(genreId)) {
            return
        }
        
        viewModelScope.launch {
            _loadingGenres.value = _loadingGenres.value + genreId
            
            movieRepository.fetchMoviesByGenre(genreId).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        // Already handled by loadingGenres
                    }
                    is Response.Success -> {
                        val movies = response.data
                        if (movies.isNotEmpty()) {
                            // Take the first movie as the representative for this genre
                            val representativeMovie = movies.first()
                            _genreMovies.value = _genreMovies.value + (genreId to representativeMovie)
                        }
                        _loadingGenres.value = _loadingGenres.value - genreId
                    }
                    is Response.Error -> {
                        _loadingGenres.value = _loadingGenres.value - genreId
                        // On error, we'll just not set a poster for this genre
                    }
                }
            }
        }
    }
    
    fun getMovieForGenre(genreId: Int): Movie? {
        return _genreMovies.value[genreId]
    }
    
    fun isLoadingGenre(genreId: Int): Boolean {
        return _loadingGenres.value.contains(genreId)
    }
}
