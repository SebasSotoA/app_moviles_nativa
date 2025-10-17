package com.app.episodic.movie_detail.data.remote.api

import com.app.episodic.BuildConfig
import com.app.episodic.movie.data.remote.models.MovieDto
import com.app.episodic.movie_detail.data.remote.models.MovieDetailDto
import com.app.episodic.movie_detail.data.remote.models.ReviewsDto
import com.app.episodic.movie_detail.data.remote.models.TvDetailMinDto
import com.app.episodic.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val MOVIE_ID = "movie_id"

interface MovieDetailApiService {

    @GET("${K.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}")
    suspend fun fetchMovieDetail(
        @Path(MOVIE_ID) movieId:Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX",
        @Query("append_to_response") appendToResponse: String = "credits,reviews"
    ): MovieDetailDto

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX",
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieDto

    @GET("${K.TV_DETAIL_ENDPOINT}/{$MOVIE_ID}")
    suspend fun fetchTvDetailMin(
        @Path(MOVIE_ID) tvId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX",
    ): TvDetailMinDto

    @GET("${K.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}/reviews")
    suspend fun fetchMovieReviews(
        @Path(MOVIE_ID) movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX",
        @Query("page") page: Int = 1
    ): ReviewsDto

}