package com.app.episodic.tv.domain.repository

import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.utils.Response
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun fetchDiscoverTv(): Flow<Response<List<Tv>>>
}


