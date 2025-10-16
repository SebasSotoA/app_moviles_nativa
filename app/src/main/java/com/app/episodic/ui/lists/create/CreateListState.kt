package com.app.episodic.ui.lists.create

import androidx.compose.runtime.Immutable

@Immutable
data class CreateListState(
    val name: String = "",
    val nameError: String? = null,
    val isSubmitting: Boolean = false
)
