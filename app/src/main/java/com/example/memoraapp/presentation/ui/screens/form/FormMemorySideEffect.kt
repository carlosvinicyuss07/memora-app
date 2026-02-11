package com.example.memoraapp.presentation.ui.screens.form

import com.example.memoraapp.presentation.ui.util.UiText

sealed interface FormMemorySideEffect {
    object CloseScreen : FormMemorySideEffect
    object NavigateToPhotoSource : FormMemorySideEffect
    data class ShowError(val message: UiText) : FormMemorySideEffect
    data class ShowSuccessMessage(val message: UiText) : FormMemorySideEffect
}