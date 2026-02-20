package com.example.memoraapp.presentation.ui.screens.auth.signup

interface SignUpScreenEvent {

    data object OnInit : SignUpScreenEvent
    data class OnFullNameChange(val value: String) : SignUpScreenEvent
    data class OnEmailChange(val value: String) : SignUpScreenEvent
    data class OnPasswordChange(val value: String) : SignUpScreenEvent
    data class OnConfirmPasswordChange(val value: String) : SignUpScreenEvent
    data object OnCreateAccountClick : SignUpScreenEvent
    data object OnContinueWithGoogleClick : SignUpScreenEvent
    data object OnLoginClick : SignUpScreenEvent
}