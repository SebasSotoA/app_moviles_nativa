package com.app.episodic.tv_detail.domain.repository

import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.utils.Response
import kotlinx.coroutines.flow.Flow

interface TvDetailRepository {
    fun fetchTvDetail(tvId: Int): Flow<Response<TvDetail>>
}


