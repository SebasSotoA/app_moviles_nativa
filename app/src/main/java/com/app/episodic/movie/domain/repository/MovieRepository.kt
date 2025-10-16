package com.app.episodic.movie.domain.repository

import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.utils.Response
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
        fun fetchDiscoverMovie(): Flow<Response<List<Movie>>>
        fun fetchTrendingMovie(): Flow<Response<List<Movie>>>
}