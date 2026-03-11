package com.example.memoraapp.presentation.ui.screens.userprofile

data class UserProfileUiState(
    val id: String? = null,
    val fullName: String = "",
    val email: String = "",
    val totalMemories: Int = 0,
    val imageUri: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
