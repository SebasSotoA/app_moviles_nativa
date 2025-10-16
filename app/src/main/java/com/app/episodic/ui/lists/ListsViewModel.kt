package com.app.episodic.ui.lists

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ListsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ListsState())
    val state: StateFlow<ListsState> = _state.asStateFlow()

    fun onToggleFavorite(id: Int) {}

    fun onMoreClicked(id: Int) {}

    fun onCreateListClick() {
        // Esta función será manejada por el callback en la UI
    }

    fun onMoreClickedCollection(collectionId: Int) {
        // TODO: Implementar menú de opciones para colección
    }

    fun onOpenCollection(collectionId: Int) {
        // TODO: Navegar a la colección específica
    }

    fun onChangeTab(tab: ListsTab) {
        _state.value = _state.value.copy(tab = tab)
    }

    // Para previews: cargar Estado con mocks
    fun loadMock() {
        _state.value =
                _state.value.copy(
                        recentItems =
                                listOf(
                                        ListItemUi(
                                                1,
                                                "Lista de Suspenso",
                                                "Thriller",
                                                true,
                                                null,
                                                4.1,
                                                false
                                        ),
                                        ListItemUi(
                                                2,
                                                "Series para Maratón",
                                                "Drama",
                                                false,
                                                null,
                                                3.8,
                                                true
                                        ),
                                        ListItemUi(
                                                3,
                                                "Recomenda de Pixar",
                                                "Animación",
                                                true,
                                                null,
                                                4.5,
                                                false
                                        ),
                                        ListItemUi(
                                                4,
                                                "Cine de Autor",
                                                "Indie",
                                                true,
                                                null,
                                                4.0,
                                                false
                                        )
                                )
                )
    }

    fun loadMockCollections() {
        _state.value = _state.value.copy(
                collections = listOf(
                        ListCollectionUi(1, "Terror", 2),
                        ListCollectionUi(2, "Románticas", 4),
                        ListCollectionUi(3, "Acción", 3),
                        ListCollectionUi(4, "Comedia", 5)
                )
        )
    }
}
