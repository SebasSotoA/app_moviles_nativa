package com.app.episodic.movie.data.repository_impl

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.movie.data.remote.api.MovieApiService
import com.app.episodic.movie.domain.models.SearchItem
import com.app.episodic.movie.domain.repository.SearchRepository
import com.app.episodic.movie_detail.data.remote.api.MovieDetailApiService
import com.app.episodic.movie_detail.data.remote.models.TvDetailMinDto
import com.app.episodic.utils.Response
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val movieDetailApiService: MovieDetailApiService,
    private val mapper: ApiMapper<List<SearchItem>, com.app.episodic.movie.data.remote.models.SearchMultiDto>
) : SearchRepository {
    override fun search(query: String): Flow<Response<List<SearchItem>>> = flow {
        emit(Response.Loading())
        val dto = movieApiService.searchMulti(query = query)
        val baseItems = mapper.mapToDomain(dto)

        val enriched = coroutineScope {
            baseItems.map { item ->
                async {
                    when (item.mediaType) {
                        "movie" -> {
                            // reutilizamos MovieDetail para runtime y género
                            val detail = movieDetailApiService.fetchMovieDetail(item.id)
                            val duration = detail.runtime // puede ser null
                            val genre = detail.genres?.firstOrNull()?.name
                            item.copy(durationMinutes = duration, genre = genre)
                        }
                        "tv" -> {
                            val tv: TvDetailMinDto = movieDetailApiService.fetchTvDetailMin(item.id)
                            val duration = tv.episodeRunTime.firstOrNull()
                            val genre = tv.genres.firstOrNull()?.name
                            item.copy(durationMinutes = duration, genre = genre)
                        }
                        else -> item
                    }
                }
            }.map { it.await() }
                .filter { it.durationMinutes == null || it.durationMinutes > 0 } // Filtrar duración 0
        }

        emit(Response.Success(enriched))
    }.catch { e ->
        emit(Response.Error(e))
    }
}


