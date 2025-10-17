package com.app.episodic.tv_detail.domain.models

data class TvDetail(
    val backdropPath: String,
    val genreIds: List<String>,
    val id: Int,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val firstAirDate: String,
    val name: String,
    val voteAverage: Double,
    val voteCount: Int,
    val cast: List<Cast>,
    val language: List<String>,
    val productionCountry: List<String>,
    val reviews: List<Review>,
    val runTime: String,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val status: String,
    val type: String,
    val lastAirDate: String,
    val nextEpisodeToAir: NextEpisodeToAir?
)

data class NextEpisodeToAir(
    val airDate: String,
    val episodeNumber: Int,
    val name: String,
    val overview: String,
    val seasonNumber: Int,
    val stillPath: String?
)

// Reutilizamos Cast y Review de movie_detail
data class Cast(
    val id: Int,
    val name: String,
    val genderRole: String,
    val character: String,
    val profilePath: String?,
) {
    private val nameParts = name.split(" ", limit = 2)
    val firstName = nameParts.getOrElse(0) { name }
    val lastName = nameParts.getOrElse(1) { "" }
}

data class Review(
    val author: String,
    val content: String,
    val id: String,
    val createdAt: String,
    val rating: Double
)


