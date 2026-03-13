package com.example.memoraapp.presentation.ui.screens.userprofile

import java.util.Date

data class UserProfileUiState(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val createdAt: Date? = null,
    val photoUrl: String? = null,
    val totalMemories: Int = 0,
    val isLoading: Boolean = false,
    val showPhotoPreview: Boolean = false,
    val errorMessage: String? = null
)
