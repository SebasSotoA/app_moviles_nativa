package com.app.episodic.utils

import android.content.Context
import com.app.MovieApplication
import com.app.episodic.R

object GenreConstants {
    // Map genre id to resource id; fallback to genre_unknown
    private val genreMap = mapOf(
        28 to R.string.genre_action,
        12 to R.string.genre_adventure,
        16 to R.string.genre_animation,
        35 to R.string.genre_comedy,
        80 to R.string.genre_crime,
        99 to R.string.genre_documentary,
        18 to R.string.genre_drama,
        10751 to R.string.genre_family,
        14 to R.string.genre_fantasy,
        36 to R.string.genre_history,
        27 to R.string.genre_horror,
        10402 to R.string.genre_music,
        9648 to R.string.genre_mystery,
        10749 to R.string.genre_romance,
        878 to R.string.genre_scifi,
        10770 to R.string.genre_tv_movie,
        53 to R.string.genre_thriller,
        10752 to R.string.genre_war,
        37 to R.string.genre_western
    )

    fun getGenreNameById(id: Int, context: Context = MovieApplication.appContext): String {
        val resId = genreMap[id] ?: R.string.genre_unknown
        return context.getString(resId)
    }

    fun getGenreIdByName(name: String, context: Context = MovieApplication.appContext): Int? {
        return genreMap.entries.find { (_, resId) -> context.getString(resId) == name }?.key
    }
}