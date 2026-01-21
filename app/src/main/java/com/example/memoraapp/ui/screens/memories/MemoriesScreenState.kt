package com.example.memoraapp.ui.screens.memories

import com.example.memoraapp.ui.util.UiText

data class MemoriesScreenState(
    val memories: List<MemoryUi> = emptyList(),
    val isLoading: Boolean = false,
    val erroMessage: UiText? = null
)
