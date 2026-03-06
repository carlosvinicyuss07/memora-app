package com.example.memoraapp.presentation.ui.screens.welcome

interface WelcomeScreenEvent {

    data class OnInit(val user: String?) : WelcomeScreenEvent
    data object OnLogoutClick : WelcomeScreenEvent
    data object OnNavigateToMemoriesClick : WelcomeScreenEvent
}