package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.domain.AuthRepository
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeScreenEvent
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeScreenSideEffect
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeScreenViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<WelcomeScreenSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: WelcomeScreenEvent) {
        when(event) {
            is WelcomeScreenEvent.OnInit -> {
                _uiState.update { it.copy(user = event.user) }
            }

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