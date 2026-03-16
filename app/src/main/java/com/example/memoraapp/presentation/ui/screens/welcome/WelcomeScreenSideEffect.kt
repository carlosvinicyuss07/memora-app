package com.example.memoraapp.presentation.ui.screens.welcome

sealed interface WelcomeScreenSideEffect {

    object CloseScreen : WelcomeScreenSideEffect
    object NavigateToMemoriesScreen : WelcomeScreenSideEffect
    data class NavigateToUserProfileScreen(val id: String) : WelcomeScreenSideEffect
}