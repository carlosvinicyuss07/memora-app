package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.domain.AuthRepository
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeScreenEvent
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeScreenSideEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WelcomeScreenViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _effects = Channel<WelcomeScreenSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: WelcomeScreenEvent) {
        when(event) {
            is WelcomeScreenEvent.OnLogoutClick -> {
                viewModelScope.launch {
                    repository.logout()
                    _effects.send(WelcomeScreenSideEffect.CloseScreen)
                }
            }

             is WelcomeScreenEvent.OnNavigateToMemoriesClick -> {
                 viewModelScope.launch {
                     _effects.send(WelcomeScreenSideEffect.NavigateToMemoriesScreen)
                 }
             }
        }
    }
}