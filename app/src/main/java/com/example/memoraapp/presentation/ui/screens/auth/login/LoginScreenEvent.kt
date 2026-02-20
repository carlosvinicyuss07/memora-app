package com.example.memoraapp.presentation.ui.screens.auth.login

interface LoginScreenEvent {

    data object OnInit : LoginScreenEvent
    data class OnEmailChange(val value: String) : LoginScreenEvent
    data class OnPasswordChange(val value: String) : LoginScreenEvent
    data object OnLoginClick : LoginScreenEvent
    data object OnContinueWithGoogleClick : LoginScreenEvent
    data object OnSignUpClick : LoginScreenEvent
}