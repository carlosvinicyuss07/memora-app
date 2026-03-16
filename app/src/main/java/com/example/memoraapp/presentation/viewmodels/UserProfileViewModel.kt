package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.R
import com.example.memoraapp.domain.AuthRepository
import com.example.memoraapp.domain.User
import com.example.memoraapp.domain.UserRepository
import com.example.memoraapp.presentation.ui.screens.userprofile.UserProfileScreenEvent
import com.example.memoraapp.presentation.ui.screens.userprofile.UserProfileSideEffect
import com.example.memoraapp.presentation.ui.screens.userprofile.UserProfileUiState
import com.example.memoraapp.presentation.ui.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<UserProfileSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: UserProfileScreenEvent) {
        when (event) {
            is UserProfileScreenEvent.OnInit -> handleOnInit(event.userId)

            is UserProfileScreenEvent.OnFullNameChange ->
                _uiState.update { it.copy(fullName = event.value) }

            is UserProfileScreenEvent.OnPhotoClick -> {
                _uiState.update { it.copy(showPhotoPreview = true) }
            }

            is UserProfileScreenEvent.OnClosePhotoPreview -> {
                _uiState.update { it.copy() }
            }

            is UserProfileScreenEvent.OnCameraClick ->
                viewModelScope.launch {
                    _effects.send(UserProfileSideEffect.NavigateToPhotoSource)
                }

            is UserProfileScreenEvent.OnSaveChanges -> handleOnSave()

            is UserProfileScreenEvent.OnLogoutClick -> {
                viewModelScope.launch {
                    authRepository.logout()
                    _effects.send(UserProfileSideEffect.NavigateToAuth)
                }
            }

            is UserProfileScreenEvent.OnDeleteMyDataClick -> {
                _uiState.update {
                    it.copy(showDeleteDialog = true)
                }
            }

            is UserProfileScreenEvent.OnDismissDeleteDialog -> {
                _uiState.update {
                    it.copy(showDeleteDialog = false)
                }
            }

            is UserProfileScreenEvent.OnConfirmDeleteAccount -> handleOnDelete(event.userId)

            is UserProfileScreenEvent.OnBackClick -> handleOnBackClick()
        }
    }

    private fun handleOnInit(id: String) {

        if (savedStateHandle.get<Boolean>("initialized") == true) return
        savedStateHandle["initialized"] = true

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            runCatching { userRepository.getUser(id) }
                .onSuccess { user ->
                    if (user != null) {
                        _uiState.update {
                            it.copy(
                                id = user.id,
                                fullName = user.fullName,
                                email = user.email,
                                photoUrl = user.photoUrl,
                                totalMemories = user.totalMemories,
                                isLoading = false
                            )
                        }
                    }
                }
                .onFailure {
                    _effects.send(UserProfileSideEffect.ShowError(UiText.StringResource(R.string.erro_ao_carregar_dados_do_usuario)))
                }
        }
    }

    private fun handleOnBackClick() {
        viewModelScope.launch {
            _effects.send(UserProfileSideEffect.CloseScreen)
        }
    }

    private fun handleOnSave() {
        val state = uiState.value

        if (state.fullName.isBlank()) {
            viewModelScope.launch {
                _effects.send(UserProfileSideEffect.ShowError(UiText.StringResource(R.string.erro_nome_obrigatorio)))
            }
            return
        }

        viewModelScope.launch {
            runCatching {
                userRepository.updateUser(
                    User(
                        id = state.id,
                        fullName = state.fullName,
                        email = state.email,
                        photoUrl = state.photoUrl,
                        createdAt = state.createdAt,
                        totalMemories = state.totalMemories
                    )
                )
            }
                .onSuccess {
                    _effects.send(UserProfileSideEffect.ShowSuccessMessage(UiText.StringResource(R.string.dados_do_usuario_atualizados_com_sucesso)))
                }
                .onFailure {
                    _effects.send(UserProfileSideEffect.ShowError(UiText.StringResource(R.string.erro_ao_tentar_atualizar_os_dados)))
                }
        }
    }

    private fun handleOnDelete(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                userRepository.deleteUser(id)
                _uiState.update { it.copy(isLoading = false) }
            }
                .onSuccess {
                    _effects.send(
                        UserProfileSideEffect.ShowSuccessMessage(
                            UiText.StringResource(
                                R.string.excluido_com_sucesso
                            )
                        )
                    )
                    _effects.send(UserProfileSideEffect.NavigateToAuth)
                }
                .onFailure {
                    _effects.send(UserProfileSideEffect.ShowError(UiText.StringResource(R.string.erro_ao_tentar_excluir)))
                }
        }
    }
}