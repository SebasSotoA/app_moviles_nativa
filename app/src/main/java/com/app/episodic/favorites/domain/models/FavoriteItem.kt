package com.app.episodic.favorites.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteItem(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String,
    val genreIds: List<Int>,
    val voteAverage: Double,
    val isMovie: Boolean,
    val addedAt: Long = System.currentTimeMillis()
)
