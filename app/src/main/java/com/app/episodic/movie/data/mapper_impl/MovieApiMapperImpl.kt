package com.app.episodic.movie.data.mapper_impl

import com.app.episodic.utils.GenreConstants
import com.app.episodic.common.data.ApiMapper
import com.app.episodic.movie.data.remote.models.MovieDto
import com.app.episodic.movie.domain.models.Movie
import com.app.MovieApplication
import com.app.episodic.R

class MovieApiMapperImpl : ApiMapper<List<Movie>, MovieDto> {
    override fun mapToDomain(apiDto: MovieDto): List<Movie> {
        return apiDto.results?.map { result ->
            Movie(
                backdropPath = formatEmptyValue(result?.backdropPath),
                genreIds = formatGenre(result?.genreIds),
                id = result?.id ?: 0,
                originalLanguage = formatEmptyValue(result?.originalLanguage, "language"),
                originalTitle = formatEmptyValue(result?.originalTitle, "title"),
                overview = formatEmptyValue(result?.overview, "overview"),
                popularity = result?.popularity ?: 0.0,
                posterPath = formatEmptyValue(result?.posterPath),
                releaseDate = formatEmptyValue(result?.releaseDate, "date"),
                title = formatEmptyValue(result?.title, "title"),
                voteAverage = result?.voteAverage ?: 0.0,
                voteCount = result?.voteCount ?: 0,
                video = result?.video ?: false
            )
        } ?: emptyList()
    }

    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if (!value.isNullOrEmpty()) return value

        val ctx = MovieApplication.appContext
        val labelRes = when (default) {
            "language" -> R.string.label_language
            "title" -> R.string.label_title
            "overview" -> R.string.label_overview
            "date" -> R.string.label_date
            else -> R.string.genre_unknown
        }

        return ctx.getString(R.string.unknown_format, ctx.getString(labelRes))
    }

    private fun formatGenre(genreIds: List<Int?>?): List<String> {
        return genreIds?.map { GenreConstants.getGenreNameById(it ?: 0) } ?: emptyList()
    }

}