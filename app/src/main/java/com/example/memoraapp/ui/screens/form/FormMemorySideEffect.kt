package com.example.memoraapp.ui.screens.form

sealed interface FormMemorySideEffect {
    object CloseScreen : FormMemorySideEffect
    object NavigateToPhotoSource : FormMemorySideEffect
    data class ShowError(val message: String) : FormMemorySideEffect
    data class ShowSuccessMessage(val message: String) : FormMemorySideEffect
}