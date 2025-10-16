package com.app.episodic.di

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.movie.data.mapper_impl.SearchMapperImpl
import com.app.episodic.movie.data.remote.api.MovieApiService
import com.app.episodic.movie.data.remote.models.SearchMultiDto
import com.app.episodic.movie.data.repository_impl.SearchRepositoryImpl
import com.app.episodic.movie.domain.models.SearchItem
import com.app.episodic.movie.domain.repository.SearchRepository
import com.app.episodic.movie_detail.data.remote.api.MovieDetailApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchMapper(): ApiMapper<List<SearchItem>, SearchMultiDto> = SearchMapperImpl()

    @Provides
    @Singleton
    fun provideSearchRepository(
        movieApiService: MovieApiService,
        movieDetailApiService: MovieDetailApiService,
        mapper: ApiMapper<List<SearchItem>, SearchMultiDto>
    ): SearchRepository = SearchRepositoryImpl(
        movieApiService = movieApiService,
        movieDetailApiService = movieDetailApiService,
        mapper = mapper
    )
}


