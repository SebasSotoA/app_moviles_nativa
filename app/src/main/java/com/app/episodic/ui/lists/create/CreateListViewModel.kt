package com.app.episodic.ui.lists.create

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class CreateListViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CreateListState())
    val state: StateFlow<CreateListState> = _state.asStateFlow()

    fun onNameChange(value: String) {
        val sanitizedValue = value.trim().replace(Regex("\\s+"), " ")
        _state.value = _state.value.copy(
            name = sanitizedValue,
            nameError = null
        )
    }

    fun onSubmit(onSuccess: (String) -> Unit) {
        val currentName = _state.value.name.trim()
        
        if (currentName.isEmpty()) {
            _state.value = _state.value.copy(
                nameError = "Ingresa un nombre válido"
            )
            return
        }
        
        if (currentName.length > 40) {
            _state.value = _state.value.copy(
                nameError = "El nombre debe tener máximo 40 caracteres"
            )
            return
        }
        
        // Validar caracteres permitidos: letras, números, espacios y "-_/&"
        val validNameRegex = Regex("^[a-zA-Z0-9\\s\\-_/&]+$")
        if (!validNameRegex.matches(currentName)) {
            _state.value = _state.value.copy(
                nameError = "Solo se permiten letras, números, espacios y los caracteres -_/&"
            )
            return
        }
        
        _state.value = _state.value.copy(
            isSubmitting = true,
            nameError = null
        )
        
        // Simular procesamiento
        onSuccess(currentName)
        
        _state.value = _state.value.copy(
            isSubmitting = false
        )
    }
}
