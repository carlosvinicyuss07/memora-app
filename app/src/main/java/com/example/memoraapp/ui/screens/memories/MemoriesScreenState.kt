package com.example.memoraapp.ui.screens.memories

data class MemoriesScreenState(
    val memories: List<MemoryUi> = emptyList(),
    val isLoading: Boolean = false,
    val erroMessage: String? = null
)
