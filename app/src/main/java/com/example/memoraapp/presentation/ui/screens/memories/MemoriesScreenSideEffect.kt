package com.example.memoraapp.presentation.ui.screens.memories

import com.example.memoraapp.presentation.ui.util.UiText

interface MemoriesScreenSideEffect {
    data class NavigateToDetail(val id: Int) : MemoriesScreenSideEffect
    data object NavigateToCreate : MemoriesScreenSideEffect
    data object NavigateToPreviousScreen : MemoriesScreenSideEffect
    data class ShowError(val message: UiText) : MemoriesScreenSideEffect
}
