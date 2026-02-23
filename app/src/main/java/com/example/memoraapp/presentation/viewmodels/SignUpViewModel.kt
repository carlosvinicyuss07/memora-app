package com.example.memoraapp.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.R
import com.example.memoraapp.data.auth.AuthRepository
import com.example.memoraapp.presentation.ui.screens.auth.signup.SignUpScreenEvent
import com.example.memoraapp.presentation.ui.screens.auth.signup.SignUpSideEffect
import com.example.memoraapp.presentation.ui.screens.auth.signup.SignUpUiState
import com.example.memoraapp.presentation.ui.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<SignUpSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.OnFullNameChange ->
                _uiState.update { it.copy(fullName = event.value) }

            is SignUpScreenEvent.OnEmailChange ->
                _uiState.update { it.copy(email = event.value) }

            is SignUpScreenEvent.OnPasswordChange ->
                _uiState.update { it.copy(password = event.value) }

            is SignUpScreenEvent.OnConfirmPasswordChange ->
                _uiState.update { it.copy(confirmPassword = event.value) }

            is SignUpScreenEvent.OnLoginClick ->
                viewModelScope.launch {
                    _effects.send(SignUpSideEffect.NavigateToLogin)
                }

            SignUpScreenEvent.OnContinueWithGoogleClick -> {
                viewModelScope.launch {
                    _effects.send(SignUpSideEffect.LaunchGoogleSignIn)
                }
            }

            is SignUpScreenEvent.OnCreateAccountClick -> register()

            is SignUpScreenEvent.OnGoogleLoginSuccess -> loginWithGoogle(event.idToken)

        }
    }

    private fun validate(state: SignUpUiState): UiText? {

        if (state.fullName.isBlank())
            return UiText.StringResource(R.string.erro_nome_obrigatorio)

        if (state.email.isBlank())
            return UiText.StringResource(R.string.erro_email_obrigatorio)

        if (!Patterns.EMAIL_ADDRESS.matcher(state.email).matches())
            return UiText.StringResource(R.string.erro_email_invalido)

        if (state.password.isBlank() || state.confirmPassword.isBlank())
            return UiText.StringResource(R.string.erro_senha_obrigatoria)

        if (state.password.length < 8)
            return UiText.StringResource(R.string.erro_senha_min_8)

        if (state.password != state.confirmPassword)
            return UiText.StringResource(R.string.erro_senhas_diferentes)

        return null
    }

    private fun register() {
        val state = uiState.value

        val validationError = validate(state)

        if (validationError != null) {
            viewModelScope.launch {
                _effects.send(SignUpSideEffect.ShowError(validationError))
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                repository.register(state.fullName, state.email, state.password)
            }
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    _effects.send(
                        SignUpSideEffect.ShowSuccessMessage(UiText.StringResource(R.string.usuario_registrado_com_sucesso))
                    )
                    _effects.send(SignUpSideEffect.NavigateToLogin)
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    _effects.send(
                        SignUpSideEffect.ShowError(UiText.StringResource(R.string.erro_ao_tentar_registrar_usuario))
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
                        SignUpSideEffect.ShowSuccessMessage(UiText.StringResource(R.string.login_bem_sucedido))
                    )
                    _effects.send(SignUpSideEffect.NavigateToHome)
                }
                .onFailure {
                    _uiState.update { it.copy(isLoading = false) }
                    _effects.send(
                        SignUpSideEffect.ShowError(UiText.StringResource(R.string.erro_login))
                    )
                }
        }
    }
}