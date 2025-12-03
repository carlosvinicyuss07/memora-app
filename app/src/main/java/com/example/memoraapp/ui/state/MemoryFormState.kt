package com.example.memoraapp.ui.state

import android.net.Uri
import com.example.memoraapp.ui.screens.form.FormMemoryScreenMode
import java.time.LocalDate


data class MemoryFormState(
    val mode: FormMemoryScreenMode = FormMemoryScreenMode.CREATE,
    val title: String = "",
    val description: String = "",
    val date: LocalDate? = null,
    val imageUri: Uri? = null,
    val isSaving: Boolean = false
)
