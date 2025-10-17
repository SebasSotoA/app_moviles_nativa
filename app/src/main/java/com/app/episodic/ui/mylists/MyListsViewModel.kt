package com.app.episodic.ui.mylists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.favorites.domain.models.FavoriteItem
import com.app.episodic.favorites.domain.repository.FavoritesRepository
import com.app.episodic.ui.mylists.components.MyListsTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListsViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(MyListsState())
    val state: StateFlow<MyListsState> = _state.asStateFlow()
    
    init {
        loadFavorites()
    }
    
    fun onTabSelected(tab: MyListsTab) {
        _state.update { it.copy(selectedTab = tab) }
    }
    
    fun onSortClick() {
        // TODO: Implementar lógica de ordenamiento
        _state.update { it.copy(showSortDialog = true) }
    }
    
    fun onFilterClick() {
        // TODO: Implementar lógica de filtrado
        _state.update { it.copy(showFilterDialog = true) }
    }
    
    fun dismissSortDialog() {
        _state.update { it.copy(showSortDialog = false) }
    }
    
    fun dismissFilterDialog() {
        _state.update { it.copy(showFilterDialog = false) }
    }
    
    fun onFavoriteToggle(itemId: Int) {
        viewModelScope.launch {
            val favorite = favoritesRepository.getFavoriteById(itemId)
            if (favorite != null) {
                favoritesRepository.removeFromFavorites(itemId)
                loadFavorites() // Recargar la lista
            }
        }
    }
    
    fun onRemoveFromFavorites(itemId: Int) {
        viewModelScope.launch {
            favoritesRepository.removeFromFavorites(itemId)
            loadFavorites() // Recargar la lista
        }
    }
    
    private fun loadFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            favoritesRepository.getAllFavorites().collect { favorites ->
                _state.update { 
                    it.copy(
                        favorites = favorites,
                        isLoading = false
                    ) 
                }
            }
        }
    }
    
    fun refreshFavorites() {
        loadFavorites()
    }
}

data class MyListsState(
    val selectedTab: MyListsTab = MyListsTab.FAVORITOS,
    val favorites: List<FavoriteItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val showSortDialog: Boolean = false,
    val showFilterDialog: Boolean = false
)

