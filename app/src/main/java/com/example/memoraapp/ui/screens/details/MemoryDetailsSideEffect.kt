package com.example.memoraapp.ui.screens.details

sealed interface MemoryDetailsSideEffect {
    object CloseScreen : MemoryDetailsSideEffect
    data class NavigateToEdit(val id: Int) : MemoryDetailsSideEffect
}