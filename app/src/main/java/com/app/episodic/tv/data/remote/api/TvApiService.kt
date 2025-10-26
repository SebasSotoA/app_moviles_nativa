package com.app.episodic.tv.data.remote.api

import com.app.episodic.BuildConfig
import com.app.episodic.tv.data.remote.models.TvDto
import com.app.episodic.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

interface TvApiService {

    @GET("discover/tv")
    suspend fun fetchDiscoverTv(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX"
    ): TvDto

    @GET("trending/tv/week")
    suspend fun fetchTrendingTv(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX"
    ): TvDto

    @GET("discover/tv")
    suspend fun fetchTvByGenre(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): TvDto

    @GET("discover/tv")
    suspend fun fetchFilteredTvShows(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX",
        @Query("with_genres") genres: String? = null,
        @Query("vote_average.gte") minRating: Float? = null,
        @Query("first_air_date_year") year: Int? = null,
        @Query("page") page: Int = 1
    ): TvDto
}

