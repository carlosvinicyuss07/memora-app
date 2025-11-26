package com.example.memoraapp.ui.screens.memories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.R
import com.example.memoraapp.data.Memory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemoriesViewModel : ViewModel() {

    private val _memories = MutableStateFlow<List<Memory>>(emptyList())
    val memories: StateFlow<List<Memory>> = _memories

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _memories.value = listOf(
                Memory(1, "Pôr do Sol na Praia", "5 de Abril, 2023", R.drawable.quadro_example_memorycard),
                Memory(2, "Luzes da Cidade", "22 de Março, 2023", R.drawable.quadro_example_memorycard),
                Memory(3, "Montanhas de Outono", "10 de Outubro, 2023", R.drawable.quadro_example_memorycard),
                Memory(4, "Café da Manhã Caseiro", "12 de Fevereiro, 2024", R.drawable.quadro_example_memorycard),
                Memory(5, "Caminhada no Parque", "1 de Junho, 2023", R.drawable.quadro_example_memorycard),
                Memory(6, "Noite de Cinema", "20 de Janeiro, 2024", R.drawable.quadro_example_memorycard)
            )
        }
    }

}