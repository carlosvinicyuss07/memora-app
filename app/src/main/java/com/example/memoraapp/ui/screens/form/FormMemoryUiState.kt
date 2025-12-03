package com.example.memoraapp.ui.screens.form

import java.time.LocalDate

data class FormMemoryUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val imageUri: String? = null,
    val isLoading: Boolean = false,
    val screenMode: FormMemoryScreenMode = FormMemoryScreenMode.CREATE
) {
    val isEditMode: Boolean get() = screenMode == FormMemoryScreenMode.EDIT
}
