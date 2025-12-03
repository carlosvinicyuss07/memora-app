package com.example.memoraapp.ui.screens.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.data.FakeMemoryRepository
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

sealed class FormMemoryUiEvent {
    object Saved : FormMemoryUiEvent()
    data class Error(val message: String) : FormMemoryUiEvent()
}

class FormMemoryViewModel(
    private val repository: MemoryRepository = FakeMemoryRepository(),
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormMemoryUiState(date = LocalDate.now()))
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<FormMemoryUiEvent>()
    val events = _events.asSharedFlow()

    private val passedId: Int? = savedStateHandle["memoryId"]

    init {
        if (passedId != null) loadMemory(passedId)
    }

    private fun loadMemory(id: Int) {
        viewModelScope.launch {
            val memory = repository.getById(id)
            memory?.let {
                _uiState.value = FormMemoryUiState(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    date = it.date,
                    imageUri = it.imageUri,
                    screenMode = FormMemoryScreenMode.EDIT
                )
            } ?: run {
                _events.emit(FormMemoryUiEvent.Error("Memória não encontrada"))
            }
        }
    }

    fun onAction(action: FormMemoryAction) {
        when (action) {
            is FormMemoryAction.OnTitleChange ->
                _uiState.value = _uiState.value.copy(title = action.value)

            is FormMemoryAction.OnDescriptionChange ->
                _uiState.value = _uiState.value.copy(description = action.value)

            is FormMemoryAction.OnDateChange ->
                _uiState.value = _uiState.value.copy(date = action.value)

            is FormMemoryAction.OnImageSelected ->
                _uiState.value = _uiState.value.copy(imageUri = action.uri)

            FormMemoryAction.OnSave -> save()
        }
    }

    private fun save() {
        viewModelScope.launch {
            val s = _uiState.value
            if (s.title.isBlank()) {
                _events.emit(FormMemoryUiEvent.Error("Título obrigatório"))
                return@launch
            }

            val memory = Memory(
                id = s.id ?: 0,
                title = s.title,
                description = s.description,
                date = s.date,
                imageUri = s.imageUri
            )

            try {
                if (s.isEditMode) {
                    repository.update(memory)
                } else {
                    repository.insert(memory)
                }
                _events.emit(FormMemoryUiEvent.Saved)
            } catch (e: Exception) {
                _events.emit(FormMemoryUiEvent.Error(e.message ?: "Erro ao salvar"))
            }
        }
    }

}