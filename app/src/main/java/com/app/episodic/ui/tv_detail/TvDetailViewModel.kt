package com.app.episodic.ui.tv_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.tv.domain.models.Tv
import com.app.episodic.tv.domain.repository.TvRepository
import com.app.episodic.tv_detail.domain.models.TvDetail
import com.app.episodic.tv_detail.domain.repository.TvDetailRepository
import com.app.episodic.utils.collectAndHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailViewModel @Inject constructor(
    private val tvDetailRepository: TvDetailRepository,
    private val tvRepository: TvRepository
) : ViewModel() {
    private val _tvDetailState = MutableStateFlow(TvDetailState())
    val tvDetailState = _tvDetailState.asStateFlow()

    fun fetchTvDetail(tvId: Int) = viewModelScope.launch {
        if (tvId == -1) {
            _tvDetailState.update {
                it.copy(isLoading = false, error = "Serie no encontrada")
            }
        } else {
            tvDetailRepository.fetchTvDetail(tvId).collectAndHandle(
                onError = { error ->
                    _tvDetailState.update {
                        it.copy(isLoading = false, error = error?.message)
                    }
                },
                onLoading = {
                    _tvDetailState.update {
                        it.copy(isLoading = true, error = null)
                    }
                }
            ) { tvDetail ->
                _tvDetailState.update {
                    it.copy(
                        isLoading = false,
                        error = null,
                        tvDetail = tvDetail
                    )
                }
            }
        }
    }

    fun fetchTvShows() = viewModelScope.launch {
        tvRepository.fetchDiscoverTv().collectAndHandle(
            onError = { error ->
                _tvDetailState.update {
                    it.copy(isTvLoading = false, error = error?.message)
                }
            },
            onLoading = {
                _tvDetailState.update {
                    it.copy(isTvLoading = true, error = null)
                }
            }
        ) { tvShows ->
            _tvDetailState.update {
                it.copy(
                    isTvLoading = false,
                    error = null,
                    tvShows = tvShows
                )
            }
        }
    }
}

data class TvDetailState(
    val tvDetail: TvDetail? = null,
    val tvShows: List<Tv> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isTvLoading: Boolean = false
)
