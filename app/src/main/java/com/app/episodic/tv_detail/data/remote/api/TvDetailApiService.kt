package com.app.episodic.tv_detail.data.remote.api

import com.app.episodic.BuildConfig
import com.app.episodic.tv_detail.data.remote.models.TvDetailDto
import com.app.episodic.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvDetailApiService {

    @GET("${K.TV_DETAIL_ENDPOINT}/{tv_id}")
    suspend fun fetchTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "es-MX",
        @Query("append_to_response") appendToResponse: String = "credits,reviews"
    ): TvDetailDto
}


