package com.app.episodic.utils



import com.app.MovieApplication
import com.app.episodic.R

object MovieGenreConstants {
    private val movieGenreMap = mapOf(
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
        10752 to R.string.genre_war,
        37 to R.string.genre_western,
        53 to R.string.genre_thriller
    )

    fun getMovieGenreNameById(id: Int): String {
        val resId = movieGenreMap[id] ?: R.string.genre_unknown
        return MovieApplication.appContext.getString(resId)
    }

    fun getAllMovieGenreIds(): List<Int> = movieGenreMap.keys.toList()

    fun getMovieGenreIdByName(name: String): Int? {
        val context = MovieApplication.appContext
        return movieGenreMap.entries.firstOrNull {
            context.getString(it.value).equals(name, ignoreCase = true)
        }?.key
    }
}


