package com.example.memoraapp.ui.state

import android.net.Uri
import java.time.LocalDate

enum class FormMode { CREATE, EDIT }

data class MemoryFormState(
    val mode: FormMode = FormMode.CREATE,
    val title: String = "",
    val description: String = "",
    val date: LocalDate? = null,
    val imageUri: Uri? = null,
    val isSaving: Boolean = false
)

sealed class MemoryFormEvent {
    object Saved : MemoryFormEvent()
    data class Error(val message: String) : MemoryFormEvent()
}
