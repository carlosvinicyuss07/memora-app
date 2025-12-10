package com.example.memoraapp.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.data.FakeMemoryRepository
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MemoriesViewModel(
    private val repository: MemoryRepository = FakeMemoryRepository()
) : ViewModel() {

    private val _memories = MutableStateFlow<List<Memory>>(emptyList())
    val memories: StateFlow<List<Memory>> = _memories

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _memories.value = listOf(
                Memory(1, "Pôr do Sol na Praia", "teste", LocalDate.now()),
                Memory(2, "Luzes da Cidade", "teste", LocalDate.now()),
                Memory(3, "Montanhas de Outono", "teste", LocalDate.now()),
                Memory(4, "Café da Manhã Caseiro", "teste", LocalDate.now()),
                Memory(5, "Caminhada no Parque", "teste", LocalDate.now()),
                Memory(6, "Noite de Cinema", "teste", LocalDate.now()),
                Memory(7, "Arte Futurística", "teste", LocalDate.now())
            )
        }
    }

}