package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.R
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.presentation.ui.extensions.toUi
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenEvent
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenSideEffect
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenState
import com.example.memoraapp.presentation.ui.util.UiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoriesViewModel(
    private val repository: MemoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemoriesScreenState())
    val uiState = _uiState.asStateFlow()

    private val _effects = Channel<MemoriesScreenSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: MemoriesScreenEvent) {
        when (event) {
            MemoriesScreenEvent.OnInit -> handleOnInit()
            MemoriesScreenEvent.OnAddMemoryClick -> handleOnClickAdd()
            MemoriesScreenEvent.OnBackClick -> handleOnBackClick()
            is MemoriesScreenEvent.OnMemoryClick -> handleOnClickMemory(event.id)
        }
    }

    private fun handleOnInit() {
        _uiState.update { it.copy(isLoading = true, erroMessage = null) }
        viewModelScope.launch {
            runCatching {
                repository.getAllMemories().collect { list ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            memories = list.map { memory -> memory.toUi() }
                        )
                    }
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        erroMessage = UiText.StringResource(R.string.erro_ao_carregar_memorias)
                    )
                }
            }
        }
    }

    private fun handleOnClickAdd() {
        viewModelScope.launch {
            _effects.send(MemoriesScreenSideEffect.NavigateToCreate)
        }
    }

    private fun handleOnBackClick() {
        viewModelScope.launch {
            _effects.send(MemoriesScreenSideEffect.NavigateToPreviousScreen)
        }
    }

    private fun handleOnClickMemory(id: Int) {
        viewModelScope.launch {
            _effects.send(MemoriesScreenSideEffect.NavigateToDetail(id))
        }
    }

}