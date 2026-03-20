package com.example.memoraapp.presentation.ui.screens.userprofile

import com.example.memoraapp.presentation.ui.util.UiText

sealed interface UserProfileSideEffect {

    object CloseScreen : UserProfileSideEffect
    object NavigateToPhotoSource : UserProfileSideEffect
    object NavigateToAuth : UserProfileSideEffect
    data class ShowError(val message: UiText) : UserProfileSideEffect
    data class ShowSuccessMessage(val message: UiText) : UserProfileSideEffect
}
