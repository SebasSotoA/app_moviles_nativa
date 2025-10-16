package com.app.episodic.movie.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchMultiDto(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<SearchResultDto>,
)

@Serializable
data class SearchResultDto(
    @SerialName("id") val id: Int,
    @SerialName("media_type") val mediaType: String, // "movie" | "tv" | "person"
    @SerialName("title") val title: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,
    @SerialName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerialName("release_date") val releaseDate: String? = null,
    @SerialName("first_air_date") val firstAirDate: String? = null,
    @SerialName("original_language") val originalLanguage: String? = null,
)


