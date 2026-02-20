package com.example.memoraapp.presentation.ui.screens.auth.login

import com.example.memoraapp.presentation.ui.util.UiText

sealed interface LoginSideEffect {
    object NavigateToHome : LoginSideEffect
    object NavigateToSignUp : LoginSideEffect
    object LaunchGoogleSignIn : LoginSideEffect
    data class ShowError(val message: UiText) : LoginSideEffect
    data class ShowSuccessMessage(val message: UiText) : LoginSideEffect
}
