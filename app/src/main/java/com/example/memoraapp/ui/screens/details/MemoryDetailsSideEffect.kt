package com.example.memoraapp.ui.screens.details

import com.example.memoraapp.ui.util.UiText

sealed interface MemoryDetailsSideEffect {
    object CloseScreen : MemoryDetailsSideEffect
    data class NavigateToEdit(val id: Int) : MemoryDetailsSideEffect
    data class ShowError(val message: UiText) : MemoryDetailsSideEffect
    data class ShowSuccessMessage(val message: UiText) : MemoryDetailsSideEffect
}