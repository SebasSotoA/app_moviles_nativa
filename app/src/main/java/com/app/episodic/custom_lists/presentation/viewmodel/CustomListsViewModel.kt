package com.app.episodic.custom_lists.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.episodic.custom_lists.domain.models.CustomList
import com.app.episodic.custom_lists.domain.models.ListItem
import com.app.episodic.custom_lists.domain.repository.CustomListsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomListsViewModel @Inject constructor(
    private val customListsRepository: CustomListsRepository
) : ViewModel() {
    
    private val _lists = MutableStateFlow<List<CustomList>>(emptyList())
    val lists: StateFlow<List<CustomList>> = _lists.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()
    
    init {
        loadLists()
    }
    
    fun loadLists() {
        viewModelScope.launch {
            _isLoading.value = true
            customListsRepository.getAllLists().collect { lists ->
                _lists.value = lists
                _isLoading.value = false
            }
        }
    }
    
    fun createList(name: String) {
        viewModelScope.launch {
            try {
                customListsRepository.createList(name)
                _toastMessage.value = "Lista creada exitosamente"
                loadLists()
            } catch (e: Exception) {
                _toastMessage.value = "Error al crear la lista"
            }
        }
    }
    
    fun addItemToList(listId: String, item: ListItem) {
        viewModelScope.launch {
            try {
                customListsRepository.addItemToList(listId, item)
                _toastMessage.value = "Agregado a la lista"
                loadLists()
            } catch (e: Exception) {
                _toastMessage.value = "Error al agregar a la lista"
            }
        }
    }
    
    fun removeItemFromList(listId: String, itemId: Int) {
        viewModelScope.launch {
            try {
                customListsRepository.removeItemFromList(listId, itemId)
                _toastMessage.value = "Eliminado de la lista"
                loadLists()
            } catch (e: Exception) {
                _toastMessage.value = "Error al eliminar de la lista"
            }
        }
    }
    
    fun renameList(listId: String, newName: String) {
        viewModelScope.launch {
            try {
                customListsRepository.renameList(listId, newName)
                _toastMessage.value = "Lista renombrada"
                loadLists()
            } catch (e: Exception) {
                _toastMessage.value = "Error al renombrar la lista"
            }
        }
    }
    
    fun deleteList(listId: String) {
        viewModelScope.launch {
            try {
                customListsRepository.deleteList(listId)
                _toastMessage.value = "Lista eliminada"
                loadLists()
            } catch (e: Exception) {
                _toastMessage.value = "Error al eliminar la lista"
            }
        }
    }
    
    fun clearToastMessage() {
        _toastMessage.value = null
    }
}
