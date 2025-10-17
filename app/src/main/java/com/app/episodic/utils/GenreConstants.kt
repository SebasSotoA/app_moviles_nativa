package com.app.episodic.utils

object GenreConstants {
    private val genreMap = mapOf(
        28 to "Acción",
        12 to "Aventura",
        16 to "Animación",
        35 to "Comedia",
        80 to "Crimen",
        99 to "Documental",
        18 to "Drama",
        10751 to "Familiar",
        14 to "Fantasía",
        36 to "Historia",
        27 to "Terror",
        10402 to "Música",
        9648 to "Misterio",
        10749 to "Romance",
        878 to "Ciencia ficción",
        10770 to "Película de TV",
        53 to "Suspenso",
        10752 to "Guerra",
        37 to "Oeste"
    )


    fun getGenreNameById(id: Int): String {
        return genreMap[id] ?: "Unknown"
    }
    
    fun getGenreIdByName(name: String): Int? {
        return genreMap.entries.find { it.value == name }?.key
    }
}