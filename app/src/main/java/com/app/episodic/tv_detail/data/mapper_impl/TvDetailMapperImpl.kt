package com.app.episodic.tv_detail.data.mapper_impl

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.tv_detail.data.remote.models.TvDetailDto
import com.app.episodic.tv_detail.domain.models.Cast
import com.app.episodic.tv_detail.domain.models.NextEpisodeToAir
import com.app.episodic.tv_detail.domain.models.Review
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.utils.LanguageConstants

class TvDetailMapperImpl : ApiMapper<TvDetail, TvDetailDto> {
    override fun mapToDomain(apiDto: TvDetailDto): TvDetail {
        return TvDetail(
            backdropPath = apiDto.backdropPath ?: "",
            genreIds = apiDto.genres?.mapNotNull { it?.name } ?: emptyList(),
            id = apiDto.id ?: 0,
            originalLanguage = apiDto.originalLanguage ?: "",
            originalName = apiDto.originalName ?: "",
            overview = apiDto.overview ?: "",
            popularity = apiDto.popularity ?: 0.0,
            posterPath = apiDto.posterPath ?: "",
            firstAirDate = apiDto.firstAirDate ?: "",
            name = apiDto.name ?: "",
            voteAverage = apiDto.voteAverage ?: 0.0,
            voteCount = apiDto.voteCount ?: 0,
            cast = apiDto.credits?.cast?.mapNotNull { castDto ->
                castDto?.let {
                    Cast(
                        id = it.id ?: 0,
                        name = it.name ?: "",
                        genderRole = when (it.gender) {
                            1 -> "Mujer"
                            2 -> "Hombre"
                            else -> "No especificado"
                        },
                        character = it.character ?: "",
                        profilePath = it.profilePath
                    )
                }
            } ?: emptyList(),
            language = apiDto.spokenLanguages?.mapNotNull { lang ->
                lang?.let { LanguageConstants.getLanguageNameByCodeOrEnglish(it.iso6391 ?: "", it.englishName) }
            } ?: emptyList(),
            productionCountry = apiDto.productionCountries?.mapNotNull { it?.name } ?: emptyList(),
            reviews = apiDto.reviews?.results?.mapNotNull { reviewDto ->
                reviewDto?.let {
                    Review(
                        author = it.author ?: "",
                        content = it.content ?: "",
                        id = it.id ?: "",
                        createdAt = it.createdAt ?: "",
                        rating = it.authorDetails?.rating ?: 0.0
                    )
                }
            } ?: emptyList(),
            runTime = apiDto.episodeRunTime?.firstOrNull()?.let { "$it Minutos" } ?: "No disponible",
            numberOfSeasons = apiDto.numberOfSeasons ?: 0,
            numberOfEpisodes = apiDto.numberOfEpisodes ?: 0,
            status = apiDto.status ?: "",
            type = apiDto.type ?: "",
            lastAirDate = apiDto.lastAirDate ?: "",
            nextEpisodeToAir = apiDto.nextEpisodeToAir?.let { nextEpisode ->
                NextEpisodeToAir(
                    airDate = nextEpisode.airDate ?: "",
                    episodeNumber = nextEpisode.episodeNumber ?: 0,
                    name = nextEpisode.name ?: "",
                    overview = nextEpisode.overview ?: "",
                    seasonNumber = nextEpisode.seasonNumber ?: 0,
                    stillPath = nextEpisode.stillPath
                )
            }
        )
    }
}
