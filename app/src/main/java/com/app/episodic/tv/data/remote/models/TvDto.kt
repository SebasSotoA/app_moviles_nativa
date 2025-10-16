package com.app.episodic.tv.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvDto(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("results")
    val results: List<TvResult?>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)

