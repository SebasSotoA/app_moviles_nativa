package com.app.episodic.di


import com.app.episodic.common.data.ApiMapper
import com.app.episodic.movie.data.mapper_impl.MovieApiMapperImpl
import com.app.episodic.movie.data.remote.api.MovieApiService
import com.app.episodic.movie.data.remote.models.MovieDto
import com.app.episodic.movie.data.repository_impl.MovieRepositoryImpl
import com.app.episodic.movie.domain.models.Movie
import com.app.episodic.movie.domain.repository.MovieRepository
import com.app.episodic.utils.K
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieApiService: MovieApiService,
        mapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieRepository = MovieRepositoryImpl(
        movieApiService, mapper
    )

    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<List<Movie>, MovieDto> = MovieApiMapperImpl()

    @Provides
    @Singleton
    fun provideMovieApiService(): MovieApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieApiService::class.java)
    }


}