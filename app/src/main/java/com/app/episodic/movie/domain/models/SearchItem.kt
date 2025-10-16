package com.app.episodic.movie.domain.models

data class SearchItem(
    val id: Int,
    val mediaType: String, // movie | tv
    val title: String,
    val posterPath: String?,
    val year: String?,
    val durationMinutes: Int?,
    val genre: String?,
    val originalLanguage: String?, // Para mostrar idioma
)



