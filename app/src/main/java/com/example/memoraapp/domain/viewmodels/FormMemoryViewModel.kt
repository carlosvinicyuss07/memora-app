package com.example.memoraapp.domain.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.data.FakeMemoryRepository
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.ui.screens.form.FormMemoryScreenEvent
import com.example.memoraapp.ui.screens.form.FormMemorySideEffect
import com.example.memoraapp.ui.screens.form.FormMemoryUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FormMemoryViewModel(
    private val repository: MemoryRepository = FakeMemoryRepository(),
    private val imagePickerViewModel: ImagePickerViewModel,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormMemoryUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<FormMemorySideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun initIfNeeded(memoryId: Int?) {
        if (savedStateHandle.get<Boolean>("initialized") == true) return

        savedStateHandle["initialized"] = true

        handleOnInit(memoryId)
    }

    fun onEvent(event: FormMemoryScreenEvent) {
        when (event) {
            is FormMemoryScreenEvent.OnTitleChange ->
                _uiState.update { it.copy(title = event.value) }

            is FormMemoryScreenEvent.OnDescriptionChange ->
                _uiState.update { it.copy(description = event.value) }

            is FormMemoryScreenEvent.OnDateChange ->
                _uiState.update { it.copy(date = event.value) }

            is FormMemoryScreenEvent.OnImageSelected ->
                _uiState.value = _uiState.value.copy(imageUri = event.uri)

            is FormMemoryScreenEvent.OnSelectPhotoClick ->
                viewModelScope.launch {
                    _effects.send(FormMemorySideEffect.NavigateToPhotoSource)
                }

            FormMemoryScreenEvent.OnBackClick -> handleOnBackClick()

            FormMemoryScreenEvent.OnSave -> {
                handleSave()
            }
        }
    }

    private fun handleOnInit(id: Int?) {
        if (id == null) return

        _uiState.update { it.copy(isLoading = true, isEditMode = true, screenName = "Editar Memória" , buttonText = "Atualizar") }

        viewModelScope.launch {
            runCatching { repository.getMemoryById(id) }
                .onSuccess { memory ->
                    if (memory != null) {
                        _uiState.update {
                            it.copy(
                                id = memory.id,
                                isLoading = false,
                                title = memory.title,
                                description = memory.description,
                                date = memory.date,
                                imageUri = memory.imageUri
                            )
                        }
                    }
                }
                .onFailure {
                    _effects.send(FormMemorySideEffect.ShowError("Erro ao carregar memória"))
                }
        }
    }

    private fun handleOnBackClick() {
        imagePickerViewModel.clear()
        viewModelScope.launch {
            _effects.send(FormMemorySideEffect.CloseScreen)
        }
    }

    private fun handleSave() {
        val state = uiState.value

        if (state.title.isBlank()) {
            viewModelScope.launch {
                _effects.send(FormMemorySideEffect.ShowError("Título é obrigatório"))
            }
            return
        }

        if (state.date == null) {
            viewModelScope.launch {
                _effects.send(FormMemorySideEffect.ShowError("Selecione uma data"))
            }
            return
        }

        if (state.imageUri == null) {
            viewModelScope.launch {
                _effects.send(FormMemorySideEffect.ShowError("É obrigatório ter uma imagem para salvar uma memória"))
            }
            return
        }

        viewModelScope.launch {
            runCatching {
                if (state.isEditMode) {
                    repository.update(
                        Memory(
                            id = state.id ?: -1,
                            title = state.title,
                            description = state.description,
                            date = state.date,
                            imageUri = state.imageUri
                        )
                    )
                } else {
                    repository.insert(
                        Memory(
                            title = state.title,
                            description = state.description,
                            date = state.date,
                            imageUri = state.imageUri
                        )
                    )
                }
            }
                .onSuccess {
                    if (!state.isEditMode) {
                        imagePickerViewModel.clear()
                    }
                    _effects.send(FormMemorySideEffect.ShowSuccessMessage(
                        if (state.isEditMode) "Atualizado com sucesso" else "Salvo com sucesso")
                    )
                    _effects.send(FormMemorySideEffect.CloseScreen)
                }
                .onFailure {
                    _effects.send(FormMemorySideEffect.ShowError(
                        if (state.isEditMode) "Erro ao atualizar" else "Erro ao salvar")
                    )
                }
        }
    }

}