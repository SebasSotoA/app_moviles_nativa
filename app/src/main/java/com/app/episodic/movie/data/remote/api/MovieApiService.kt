package com.app.episodic.movie.data.remote.api

import com.app.episodic.utils.K
import com.app.episodic.movie.data.remote.models.MovieDto
import retrofit2.http.GET
import retrofit2.http.Query
import com.app.episodic.BuildConfig
import com.app.episodic.movie.data.remote.models.SearchMultiDto

interface MovieApiService {

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-ES"
    ): MovieDto

    @GET(K.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX"
    ): MovieDto

    @GET(K.SEARCH_MULTI_ENDPOINT)
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("page") page: Int = 1
    ): SearchMultiDto

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchMoviesByGenre(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): MovieDto
}