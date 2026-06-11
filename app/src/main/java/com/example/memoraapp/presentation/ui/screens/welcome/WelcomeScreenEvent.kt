package com.example.memoraapp.presentation.ui.screens.welcome

interface WelcomeScreenEvent {

    data class OnInit(val userName: String?, val userId: String?) : WelcomeScreenEvent
    data object OnLogoutClick : WelcomeScreenEvent
    data class OnNavigateToUserProfileClick(val id: String) : WelcomeScreenEvent
    data object OnNavigateToMemoriesClick : WelcomeScreenEvent
}