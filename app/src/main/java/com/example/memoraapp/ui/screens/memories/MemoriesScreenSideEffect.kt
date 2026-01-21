package com.example.memoraapp.ui.screens.memories

import com.example.memoraapp.ui.util.UiText

interface MemoriesScreenSideEffect {
    data class NavigateToDetail(val id: Int) : MemoriesScreenSideEffect
    data object NavigateToCreate : MemoriesScreenSideEffect
    data object NavigateToPreviousScreen : MemoriesScreenSideEffect
    data class ShowError(val message: UiText) : MemoriesScreenSideEffect
}
