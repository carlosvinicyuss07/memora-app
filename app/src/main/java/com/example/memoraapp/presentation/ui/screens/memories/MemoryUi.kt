package com.example.memoraapp.presentation.ui.screens.memories

data class MemoryUi(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val imageUri: String? = null
)
