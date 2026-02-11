package com.example.memoraapp.presentation.ui.screens.memories

import com.example.memoraapp.presentation.ui.util.UiText

data class MemoriesScreenState(
    val memories: List<MemoryUi> = emptyList(),
    val isLoading: Boolean = false,
    val erroMessage: UiText? = null
)
