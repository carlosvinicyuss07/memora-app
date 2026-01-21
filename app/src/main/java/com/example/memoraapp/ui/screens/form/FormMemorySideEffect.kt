package com.example.memoraapp.ui.screens.form

import com.example.memoraapp.ui.util.UiText

sealed interface FormMemorySideEffect {
    object CloseScreen : FormMemorySideEffect
    object NavigateToPhotoSource : FormMemorySideEffect
    data class ShowError(val message: UiText) : FormMemorySideEffect
    data class ShowSuccessMessage(val message: UiText) : FormMemorySideEffect
}