package com.example.memoraapp.ui.screens.memories

interface MemoriesScreenSideEffect {
    data class NavigateToDetail(val id: Int) : MemoriesScreenSideEffect
    data object NavigateToCreate : MemoriesScreenSideEffect
    data object NavigateToPreviousScreen : MemoriesScreenSideEffect
    data class ShowError(val message: String) : MemoriesScreenSideEffect
}
