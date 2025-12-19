package com.example.memoraapp.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.data.FakeMemoryRepository
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.domain.formattedToString
import com.example.memoraapp.ui.screens.details.MemoryDetailsScreenEvent
import com.example.memoraapp.ui.screens.details.MemoryDetailsSideEffect
import com.example.memoraapp.ui.screens.details.MemoryDetailsUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoryDetailsViewModel(
    private val repository: MemoryRepository = FakeMemoryRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemoryDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<MemoryDetailsSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: MemoryDetailsScreenEvent) {
        when(event) {
            is MemoryDetailsScreenEvent.OnInit -> handleOnInit(event.memoryId)

            is MemoryDetailsScreenEvent.OnEditClick -> handleOnEdit(event.memoryId)

            is MemoryDetailsScreenEvent.OnDeleteClick -> handleOnDelete(event.memoryId)

            MemoryDetailsScreenEvent.OnBackClick -> handleOnBackClick()
        }
    }

    fun handleOnInit(id: Int?) {
        if (id == null) return

        _uiState.update { it.copy(isLoading = true) }

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
                                date = memory.date.formattedToString()
                            )
                        }
                    }
                }
                .onFailure {
                    _effects.send(MemoryDetailsSideEffect.ShowError("Erro ao carregar memória"))
                }
        }
    }

    fun handleOnEdit(id: Int?) {
        if (id == null) return

        viewModelScope.launch {
            _effects.send(MemoryDetailsSideEffect.NavigateToEdit(id))
        }
    }

    fun handleOnDelete(id: Int?) {
        if (id == null) return

        viewModelScope.launch {
            runCatching {
                repository.delete(id)
            }
                .onSuccess {
                    _effects.send(MemoryDetailsSideEffect.ShowSuccessMessage("Excluído com sucesso!"))
                    _effects.send(MemoryDetailsSideEffect.CloseScreen)
                }
                .onFailure {
                    _effects.send(MemoryDetailsSideEffect.ShowError("Erro ao tentar excluir"))
                }
        }
    }

    fun handleOnBackClick() {
        viewModelScope.launch {
            _effects.send(MemoryDetailsSideEffect.CloseScreen)
        }
    }
}