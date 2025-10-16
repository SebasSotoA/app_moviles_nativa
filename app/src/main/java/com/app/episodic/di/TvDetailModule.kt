package com.app.episodic.di

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.tv_detail.data.mapper_impl.TvDetailMapperImpl
import com.app.episodic.tv_detail.data.remote.api.TvDetailApiService
import com.app.episodic.tv_detail.data.remote.models.TvDetailDto
import com.app.episodic.tv_detail.data.repository_impl.TvDetailRepoImpl
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.tv_detail.domain.repository.TvDetailRepository
import com.app.episodic.utils.K
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TvDetailModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideTvDetailRepository(
        tvDetailApiService: TvDetailApiService,
        apiDetailMapper: ApiMapper<TvDetail, TvDetailDto>
    ): TvDetailRepository = TvDetailRepoImpl(
        tvDetailApiService = tvDetailApiService,
        apiDetailMapper = apiDetailMapper
    )

    @Provides
    @Singleton
    fun provideTvDetailMapper(): ApiMapper<TvDetail, TvDetailDto> = TvDetailMapperImpl()

    @Provides
    @Singleton
    fun provideTvDetailApiService(): TvDetailApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(TvDetailApiService::class.java)
    }
}


