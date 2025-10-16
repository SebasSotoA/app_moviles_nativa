package com.app.episodic.tv.domain.models

data class Tv(
    val backdropPath: String,
    val genreIds: List<String>,
    val id: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val firstAirDate: String,
    val name: String,
    val voteAverage: Double,
    val voteCount: Int
)

