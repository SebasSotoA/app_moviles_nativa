package com.app.episodic.favorites.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.favorites.domain.models.FavoriteItem
import com.app.episodic.favorites.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    
    private val _isFavorite = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val isFavorite: StateFlow<Map<Int, Boolean>> = _isFavorite.asStateFlow()
    
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()
    
    fun checkIfFavorite(itemId: Int) {
        viewModelScope.launch {
            val favorite = favoritesRepository.isFavorite(itemId)
            _isFavorite.value = _isFavorite.value + (itemId to favorite)
        }
    }
    
    fun toggleFavorite(item: FavoriteItem) {
        viewModelScope.launch {
            val isCurrentlyFavorite = favoritesRepository.isFavorite(item.id)
            if (isCurrentlyFavorite) {
                favoritesRepository.removeFromFavorites(item.id)
                _toastMessage.value = "Se quitó de favoritos"
            } else {
                favoritesRepository.addToFavorites(item)
                _toastMessage.value = "Se agregó a favoritos"
            }
            _isFavorite.value = _isFavorite.value + (item.id to !isCurrentlyFavorite)
        }
    }
    
    fun addToFavorites(item: FavoriteItem) {
        viewModelScope.launch {
            favoritesRepository.addToFavorites(item)
            _toastMessage.value = "Se agregó a favoritos"
            _isFavorite.value = _isFavorite.value + (item.id to true)
        }
    }
    
    fun removeFromFavorites(itemId: Int) {
        viewModelScope.launch {
            favoritesRepository.removeFromFavorites(itemId)
            _toastMessage.value = "Se quitó de favoritos"
            _isFavorite.value = _isFavorite.value + (itemId to false)
        }
    }
    
    fun clearToastMessage() {
        _toastMessage.value = null
    }
}
