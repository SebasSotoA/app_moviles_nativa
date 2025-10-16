package com.app.episodic.di

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.tv.data.mapper_impl.TvMapperImpl
import com.app.episodic.tv.data.remote.api.TvApiService
import com.app.episodic.tv.data.remote.models.TvDto
import com.app.episodic.tv.data.repository_impl.TvRepositoryImpl
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv.domain.repository.TvRepository
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
object TvModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideTvRepository(
        tvApiService: TvApiService,
        mapper: ApiMapper<List<Tv>, TvDto>
    ): TvRepository = TvRepositoryImpl(
        tvApiService, mapper
    )

    @Provides
    @Singleton
    fun provideTvMapper(): ApiMapper<List<Tv>, TvDto> = TvMapperImpl()

    @Provides
    @Singleton
    fun provideTvApiService(): TvApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(TvApiService::class.java)
    }
}


