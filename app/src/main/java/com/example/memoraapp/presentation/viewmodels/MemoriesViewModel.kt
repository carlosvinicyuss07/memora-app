package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.presentation.ui.extensions.toUi
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenEvent
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenSideEffect
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenState
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

    private val _events = Channel<MemoriesScreenSideEffect>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        observeMemories()
    }

    fun onEvent(event: MemoriesScreenEvent) {
        when (event) {
            MemoriesScreenEvent.OnInit -> handleOnInit()
            MemoriesScreenEvent.OnRefresh -> handleOnRefresh()
            MemoriesScreenEvent.OnAddMemoryClick -> handleOnClickAdd()
            MemoriesScreenEvent.OnBackClick -> handleOnBackClick()
            is MemoriesScreenEvent.OnMemoryClick -> handleOnClickMemory(event.id)
        }
    }

    private fun observeMemories() {
        viewModelScope.launch {
            repository.getAllMemories()
                .collect { list ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            memories = list.map { memory -> memory.toUi() }
                        )
                    }
                }
        }
    }

    private fun handleOnInit() {
        _uiState.update { it.copy(isLoading = true, erroMessage = null) }
    }

    private fun handleOnRefresh() = handleOnInit()

    private fun handleOnClickAdd() {
        viewModelScope.launch {
            _events.send(MemoriesScreenSideEffect.NavigateToCreate)
        }
    }

    private fun handleOnBackClick() {
        viewModelScope.launch {
            _events.send(MemoriesScreenSideEffect.NavigateToPreviousScreen)
        }
    }

    private fun handleOnClickMemory(id: Int) {
        viewModelScope.launch {
            _events.send(MemoriesScreenSideEffect.NavigateToDetail(id))
        }
    }

}