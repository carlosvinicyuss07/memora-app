package com.example.memoraapp.presentation.ui.screens.auth.login

interface LoginScreenEvent {

    data object OnInit : LoginScreenEvent
    data class OnEmailChange(val value: String) : LoginScreenEvent
    data class OnPasswordChange(val value: String) : LoginScreenEvent
    data object OnLoginClick : LoginScreenEvent
    object OnContinueWithGoogleClick : LoginScreenEvent
    data class OnGoogleLoginSuccess(val idToken: String) : LoginScreenEvent
    data object OnSignUpClick : LoginScreenEvent
}