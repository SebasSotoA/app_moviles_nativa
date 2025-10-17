package com.app.episodic.movie.data.mapper_impl

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.movie.data.remote.models.SearchMultiDto
import com.app.episodic.movie.domain.models.SearchItem

class SearchMapperImpl : ApiMapper<List<SearchItem>, SearchMultiDto> {
    override fun mapToDomain(apiDto: SearchMultiDto): List<SearchItem> {
        return apiDto.results
            .filter { it.mediaType == "movie" || it.mediaType == "tv" }
            .filter { it.posterPath != null && it.posterPath.isNotEmpty() }
            .map { result ->
                val title = result.title ?: result.name ?: ""
                val year = (result.releaseDate ?: result.firstAirDate)?.take(4)
                SearchItem(
                    id = result.id,
                    mediaType = result.mediaType,
                    title = title,
                    posterPath = result.posterPath,
                    year = year,
                    durationMinutes = null,
                    genre = null,
                    originalLanguage = result.originalLanguage
                )
            }
    }
}



