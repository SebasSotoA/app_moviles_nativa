package com.app.episodic.utils

import com.app.MovieApplication
import com.app.episodic.R

object TvGenreConstants {
    private val tvGenreMap = mapOf(
        10759 to R.string.genre_action, // Action & Adventure -> usar label acción
        16 to R.string.genre_animation,
        35 to R.string.genre_comedy,
        80 to R.string.genre_crime,
        99 to R.string.genre_documentary,
        18 to R.string.genre_drama,
        10751 to R.string.genre_family,
        10762 to R.string.genre_unknown, // Kids - no local string available
        9648 to R.string.genre_mystery,
        10763 to R.string.genre_unknown, // News
        10764 to R.string.genre_unknown, // Reality
        10765 to R.string.genre_scifi,
        10766 to R.string.genre_unknown, // Soap
        10767 to R.string.genre_unknown, // Talk
        10768 to R.string.genre_war,
        37 to R.string.genre_western
    )

    fun getTvGenreNameById(id: Int): String {
        val resId = tvGenreMap[id] ?: R.string.genre_unknown
        return MovieApplication.appContext.getString(resId)
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
