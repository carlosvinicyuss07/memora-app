package com.example.memoraapp.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.R
import com.example.memoraapp.data.auth.AuthRepository
import com.example.memoraapp.presentation.ui.screens.auth.login.LoginScreenEvent
import com.example.memoraapp.presentation.ui.screens.auth.login.LoginSideEffect
import com.example.memoraapp.presentation.ui.screens.auth.login.LoginUiState
import com.example.memoraapp.presentation.ui.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<LoginSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.OnEmailChange ->
                _uiState.update { it.copy(email = event.value) }

            is LoginScreenEvent.OnPasswordChange ->
                _uiState.update { it.copy(password = event.value) }

            is LoginScreenEvent.OnLoginClick -> login()

            LoginScreenEvent.OnContinueWithGoogleClick -> {
                viewModelScope.launch {
                    _effects.send(LoginSideEffect.LaunchGoogleSignIn)
                }
            }

            is LoginScreenEvent.OnGoogleLoginSuccess -> loginWithGoogle(event.idToken)

        }
    }

    private fun validate(state: LoginUiState): UiText? {

        if (state.email.isBlank())
            return UiText.StringResource(R.string.erro_email_obrigatorio)

        if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches())
            return UiText.StringResource(R.string.erro_email_invalido)

        if (state.password.isBlank())
            return UiText.StringResource(R.string.erro_senha_obrigatoria)

        if (state.password.length < 8)
            return UiText.StringResource(R.string.erro_senha_min_8)

        return null
    }

    private fun login() {
        val state = uiState.value

        val validationError = validate(state)

        if (validationError != null) {
            viewModelScope.launch {
                _effects.send(LoginSideEffect.ShowError(validationError))
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                repository.login(state.email, state.password)
            }
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    _effects.send(
                        LoginSideEffect.ShowSuccessMessage(UiText.StringResource(R.string.login_bem_sucedido))
                    )
                    _effects.send(LoginSideEffect.NavigateToHome)
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    _effects.send(
                        LoginSideEffect.ShowError(UiText.StringResource(R.string.erro_login))
                    )
                }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                repository.loginWithGoogle(idToken)
            }
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    _effects.send(
                        LoginSideEffect.ShowSuccessMessage(UiText.StringResource(R.string.login_bem_sucedido))
                    )
                    _effects.send(LoginSideEffect.NavigateToHome)
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    _effects.send(
                        LoginSideEffect.ShowError(UiText.StringResource(R.string.erro_login))
                    )
                }
        }
    }
}
