package com.app.episodic.utils

object TvGenreConstants {
    private val tvGenreMap = mapOf(
        10759 to "Action & Adventure",
        16 to "Animación",
        35 to "Comedia",
        80 to "Crimen",
        99 to "Documental",
        18 to "Drama",
        10751 to "Familia",
        10762 to "Kids",
        9648 to "Misterio",
        10763 to "News",
        10764 to "Reality",
        10765 to "Sci-Fi & Fantasy",
        10766 to "Soap",
        10767 to "Talk",
        10768 to "War & Politics",
        37 to "Western"
    )

    fun getTvGenreNameById(id: Int): String {
        return tvGenreMap[id] ?: "Unknown"
    }
    
    fun getAllTvGenreIds(): List<Int> {
        return tvGenreMap.keys.toList()
    }
    
    // Función para mapear géneros de películas a géneros de TV equivalentes
    fun getEquivalentTvGenreId(movieGenreId: Int): Int? {
        return when (movieGenreId) {
            28 -> 10759 // Acción -> Action & Adventure
            12 -> 10759 // Aventura -> Action & Adventure
            16 -> 16    // Animación -> Animación
            35 -> 35    // Comedia -> Comedia
            80 -> 80    // Crimen -> Crimen
            99 -> 99    // Documental -> Documental
            18 -> 18    // Drama -> Drama
            10751 -> 10751 // Familiar -> Familia
            14 -> 10765 // Fantasía -> Sci-Fi & Fantasy
            27 -> 9648  // Terror -> Misterio (más cercano)
            878 -> 10765 // Ciencia ficción -> Sci-Fi & Fantasy
            10749 -> 18 // Romance -> Drama (más cercano)
            53 -> 9648  // Suspenso -> Misterio
            10752 -> 10768 // Guerra -> War & Politics
            37 -> 37    // Oeste -> Western
            else -> null
        }
    }
}
