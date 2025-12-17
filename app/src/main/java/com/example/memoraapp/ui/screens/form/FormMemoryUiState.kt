package com.example.memoraapp.ui.screens.form

import android.net.Uri
import java.time.LocalDate

data class FormMemoryUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: LocalDate? = null,
    val imageUri: Uri? = null,
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val errorMessage: String? = null,
    val screenName: String = "Nova Memória",
    val buttonText: String = "Salvar"
)