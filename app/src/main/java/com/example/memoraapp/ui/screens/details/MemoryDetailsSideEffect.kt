package com.example.memoraapp.ui.screens.details

import com.example.memoraapp.ui.screens.form.FormMemorySideEffect

sealed interface MemoryDetailsSideEffect {
    object CloseScreen : MemoryDetailsSideEffect
    data class NavigateToEdit(val id: Int) : MemoryDetailsSideEffect
    data class ShowError(val message: String) : MemoryDetailsSideEffect
    data class ShowSuccessMessage(val message: String) : MemoryDetailsSideEffect
}