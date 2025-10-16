package com.app.episodic.movie_detail.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvDetailMinDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("genres") val genres: List<GenreDto> = emptyList(),
    @SerialName("episode_run_time") val episodeRunTime: List<Int> = emptyList(),
    @SerialName("first_air_date") val firstAirDate: String? = null,
)

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String
)



