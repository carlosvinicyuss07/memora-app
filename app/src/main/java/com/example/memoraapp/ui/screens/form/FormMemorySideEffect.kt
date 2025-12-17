package com.example.memoraapp.ui.screens.form

sealed interface FormMemorySideEffect {
    object CloseScreen : FormMemorySideEffect
    data class NavigateToPhotoSource(val memoryId: Long?) : FormMemorySideEffect
    data class ShowError(val message: String) : FormMemorySideEffect
    data class ShowSuccessMessage(val message: String) : FormMemorySideEffect
}