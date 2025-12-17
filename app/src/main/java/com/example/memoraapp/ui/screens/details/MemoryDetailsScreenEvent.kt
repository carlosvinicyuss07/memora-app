package com.example.memoraapp.ui.screens.details

interface MemoryDetailsScreenEvent {

    data class OnInit(val memoryId: Int?) : MemoryDetailsScreenEvent
    object OnEditClick : MemoryDetailsScreenEvent
    object OnDeleteClick : MemoryDetailsScreenEvent
    data object OnBackClick : MemoryDetailsScreenEvent
}