package com.app.episodic.tv.data.repository_impl

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.tv.data.remote.api.TvApiService
import com.app.episodic.tv.data.remote.models.TvDto
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv.domain.repository.TvRepository
import com.app.episodic.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TvRepositoryImpl(
    private val tvApiService: TvApiService,
    private val apiMapper: ApiMapper<List<Tv>, TvDto>
) : TvRepository {
    override fun fetchDiscoverTv(): Flow<Response<List<Tv>>> = flow {
        emit(Response.Loading())
        val tvDto = tvApiService.fetchDiscoverTv()
        apiMapper.mapToDomain(tvDto).apply {
            emit(Response.Success(this))
        }
    }.catch { e ->
        emit(Response.Error(e))
    }

    override fun fetchTrendingTv(): Flow<Response<List<Tv>>> = flow {
        emit(Response.Loading())
        val tvDto = tvApiService.fetchTrendingTv()
        apiMapper.mapToDomain(tvDto).apply {
            emit(Response.Success(this))
        }
    }.catch { e ->
        emit(Response.Error(e))
    }
}


