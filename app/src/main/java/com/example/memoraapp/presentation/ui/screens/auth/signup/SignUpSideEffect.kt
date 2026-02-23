package com.example.memoraapp.presentation.ui.screens.auth.signup

import com.example.memoraapp.presentation.ui.util.UiText

sealed interface SignUpSideEffect {

    object NavigateToLogin : SignUpSideEffect
    object NavigateToHome : SignUpSideEffect
    object LaunchGoogleSignIn : SignUpSideEffect
    data class ShowError(val message: UiText) : SignUpSideEffect
    data class ShowSuccessMessage(val message: UiText) : SignUpSideEffect
}