package com.example.memoraapp.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.data.FakeMemoryRepository
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.domain.toUi
import com.example.memoraapp.ui.screens.memories.MemoriesScreenEvent
import com.example.memoraapp.ui.screens.memories.MemoriesScreenSideEffect
import com.example.memoraapp.ui.screens.memories.MemoriesScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class MemoriesViewModel(
    private val repository: MemoryRepository = FakeMemoryRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemoriesScreenState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MemoriesScreenSideEffect>()
    val events = _events.asSharedFlow()

    fun onEvent(event: MemoriesScreenEvent) {
        when (event) {
            MemoriesScreenEvent.OnInit -> handleOnInit()
            MemoriesScreenEvent.OnRefresh -> handleOnRefresh()
            MemoriesScreenEvent.OnAddMemoryClick -> handleOnClickAdd()
            MemoriesScreenEvent.OnBackClick -> handleOnBackClick()
            is MemoriesScreenEvent.OnMemoryClick -> handleOnClickMemory(event.id)
        }
    }

    private fun handleOnInit() {
        _uiState.update { it.copy(isLoading = true, erroMessage = null) }

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

    private fun handleOnRefresh() = handleOnInit()

    private fun handleOnClickAdd() {
        viewModelScope.launch {
            _events.emit(MemoriesScreenSideEffect.NavigateToCreate)
        }
    }

    private fun handleOnBackClick() {
        viewModelScope.launch {
            _events.emit(MemoriesScreenSideEffect.NavigateToWelcome)
        }
    }

    private fun handleOnClickMemory(id: Int) {
        viewModelScope.launch {
            _events.emit(MemoriesScreenSideEffect.NavigateToDetail(id))
        }
    }

}