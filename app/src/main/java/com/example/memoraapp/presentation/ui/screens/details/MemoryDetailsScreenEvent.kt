package com.example.memoraapp.presentation.ui.screens.details

interface MemoryDetailsScreenEvent {

    data class OnInit(val memoryId: String?) : MemoryDetailsScreenEvent
    data class OnEditClick(val memoryId: String?) : MemoryDetailsScreenEvent
    data class OnDeleteClick(val memoryId: String?) : MemoryDetailsScreenEvent
    data object OnBackClick : MemoryDetailsScreenEvent
}