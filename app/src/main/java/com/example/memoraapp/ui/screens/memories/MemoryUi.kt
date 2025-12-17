package com.example.memoraapp.ui.screens.memories

data class MemoryUi(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val imageUri: String? = null
)
