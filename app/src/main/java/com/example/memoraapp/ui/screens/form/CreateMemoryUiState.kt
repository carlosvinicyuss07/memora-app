package com.example.memoraapp.ui.screens.form

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.memoraapp.ui.state.FormMode
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class CreateMemoryUiState(
    val mode: FormMode = FormMode.CREATE,
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val imageUri: Uri? = null,
    val isLoading: Boolean = false
) {
    val isEditMode: Boolean get() = mode == FormMode.EDIT
}
