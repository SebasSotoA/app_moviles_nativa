package com.app.episodic.movie_detail.domain

import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie_detail.domain.models.MovieDetail
import com.app.episodic.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {
    fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>>
    fun fetchMovie(): Flow<Response<List<Movie>>>
}