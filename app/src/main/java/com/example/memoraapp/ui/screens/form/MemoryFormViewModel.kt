package com.example.memoraapp.ui.screens.form

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.data.repository.FakeMemoryRepository
import com.example.memoraapp.data.repository.MemoryRepository
import com.example.memoraapp.ui.state.FormMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MemoryFormViewModel(
    private val repository: MemoryRepository = FakeMemoryRepository(),
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val memoryId: Int? = savedStateHandle["memoryId"]

    @SuppressLint("NewApi")
    private val _uiState = MutableStateFlow(CreateMemoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (memoryId != null) {

        }
    }

}