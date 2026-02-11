package com.example.memoraapp.presentation.ui.screens.details

interface MemoryDetailsScreenEvent {

    data class OnInit(val memoryId: Int?) : MemoryDetailsScreenEvent
    data class OnEditClick(val memoryId: Int?) : MemoryDetailsScreenEvent
    data class OnDeleteClick(val memoryId: Int?) : MemoryDetailsScreenEvent
    data object OnBackClick : MemoryDetailsScreenEvent
}