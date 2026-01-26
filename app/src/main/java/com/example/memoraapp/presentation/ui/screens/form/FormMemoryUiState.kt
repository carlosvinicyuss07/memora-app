package com.example.memoraapp.presentation.ui.screens.form

import com.example.memoraapp.presentation.ui.util.UiText
import java.time.LocalDate

data class FormMemoryUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: LocalDate? = null,
    val imageUri: String? = null,
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val errorMessage: String? = null,
    val screenName: UiText? = null,
    val buttonText: UiText? = null
)