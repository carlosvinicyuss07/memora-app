package com.example.memoraapp.presentation.ui.screens.form

import java.time.LocalDate

interface FormMemoryScreenEvent {

    data class OnInit(val memoryId: Int?) : FormMemoryScreenEvent
    data class OnTitleChange(val value: String) : FormMemoryScreenEvent
    data class OnDescriptionChange(val value: String) : FormMemoryScreenEvent
    data class OnDateChange(val value: LocalDate) : FormMemoryScreenEvent
    data class OnImageSelected(val uri: String?) : FormMemoryScreenEvent
    object OnSave : FormMemoryScreenEvent
    object OnSelectPhotoClick : FormMemoryScreenEvent
    data object OnBackClick : FormMemoryScreenEvent
}
