package com.app.episodic.custom_lists.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CustomList(
    val id: String,
    val name: String,
    val items: List<ListItem>,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Serializable
data class ListItem(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val posterPath: String,
    val genreIds: List<Int>,
    val voteAverage: Double,
    val isMovie: Boolean,
    val addedAt: Long = System.currentTimeMillis()
)
