package com.example.memoraapp.ui.screens.details

import android.net.Uri

data class MemoryDetailsUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val imageUri: Uri? = null,
    val isLoading: Boolean = false
)