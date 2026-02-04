package com.example.memoraapp.presentation.ui.screens.details

data class MemoryDetailsUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val imageUri: String? = null,
    val isLoading: Boolean = false
)