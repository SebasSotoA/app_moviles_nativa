package com.app.episodic.movie.data.remote.api

import com.app.episodic.utils.K
import com.app.episodic.movie.data.remote.models.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query
import com.app.episodic.BuildConfig

interface MovieApiService {

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-ES"
    ): MovieDto

    @GET(K.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-ES"
    ): MovieDto
}