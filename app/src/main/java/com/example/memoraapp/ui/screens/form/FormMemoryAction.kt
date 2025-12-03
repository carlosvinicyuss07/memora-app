package com.example.memoraapp.ui.screens.form

import java.time.LocalDate

interface FormMemoryAction {
    data class OnTitleChange(val value: String) : FormMemoryAction
    data class OnDescriptionChange(val value: String) : FormMemoryAction
    data class OnDateChange(val value: LocalDate) : FormMemoryAction
    data class OnImageSelected(val uri: String?) : FormMemoryAction
    object OnSave : FormMemoryAction
}
