package com.app.episodic.movie.domain.repository

import com.app.episodic.movie.domain.models.SearchItem
import com.app.episodic.utils.Response
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(query: String): Flow<Response<List<SearchItem>>>
}



