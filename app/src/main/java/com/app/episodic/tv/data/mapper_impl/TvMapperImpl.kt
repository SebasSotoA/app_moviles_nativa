package com.app.episodic.tv.data.mapper_impl

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.tv.data.remote.models.TvDto
import com.app.episodic.tv.domain.models.Tv

class TvMapperImpl : ApiMapper<List<Tv>, TvDto> {
    override fun mapToDomain(apiDto: TvDto): List<Tv> {
        return apiDto.results?.mapNotNull { result ->
            result?.let {
                Tv(
                    backdropPath = it.backdropPath ?: "",
                    genreIds = it.genreIds?.mapNotNull { id -> id?.toString() } ?: emptyList(),
                    id = it.id ?: 0,
                    originCountry = it.originCountry?.mapNotNull { country -> country } ?: emptyList(),
                    originalLanguage = it.originalLanguage ?: "",
                    originalName = it.originalName ?: "",
                    overview = it.overview ?: "",
                    popularity = it.popularity ?: 0.0,
                    posterPath = it.posterPath ?: "",
                    firstAirDate = it.firstAirDate ?: "",
                    name = it.name ?: "",
                    voteAverage = it.voteAverage ?: 0.0,
                    voteCount = it.voteCount ?: 0
                )
            }
        } ?: emptyList()
    }
}

