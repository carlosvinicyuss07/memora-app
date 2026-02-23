package com.example.memoraapp.presentation.ui.screens.auth.signup

import com.example.memoraapp.presentation.ui.screens.auth.login.LoginScreenEvent

interface SignUpScreenEvent {

    data class OnFullNameChange(val value: String) : SignUpScreenEvent
    data class OnEmailChange(val value: String) : SignUpScreenEvent
    data class OnPasswordChange(val value: String) : SignUpScreenEvent
    data class OnConfirmPasswordChange(val value: String) : SignUpScreenEvent
    data object OnCreateAccountClick : SignUpScreenEvent
    data object OnContinueWithGoogleClick : SignUpScreenEvent
    data class OnGoogleLoginSuccess(val idToken: String) : SignUpScreenEvent
    data object OnLoginClick : SignUpScreenEvent
}