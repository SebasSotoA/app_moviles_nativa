package com.app.episodic.tv_detail.data.repository_impl

import com.app.episodic.common.data.ApiMapper
import com.app.episodic.tv_detail.data.remote.api.TvDetailApiService
import com.app.episodic.tv_detail.data.remote.models.TvDetailDto
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.tv_detail.domain.repository.TvDetailRepository
import com.app.episodic.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TvDetailRepoImpl(
    private val tvDetailApiService: TvDetailApiService,
    private val apiDetailMapper: ApiMapper<TvDetail, TvDetailDto>
) : TvDetailRepository {
    override fun fetchTvDetail(tvId: Int): Flow<Response<TvDetail>> = flow {
        emit(Response.Loading())
        val tvDetailDto = tvDetailApiService.fetchTvDetail(tvId)
        val tvDetail = apiDetailMapper.mapToDomain(tvDetailDto)
        emit(Response.Success(tvDetail))
    }.catch { e ->
        emit(Response.Error(e))
    }
}


