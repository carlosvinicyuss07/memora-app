package com.example.memoraapp.presentation.ui.screens.details

interface MemoryDetailsScreenEvent {

    data class OnInit(val memoryId: String?) : MemoryDetailsScreenEvent
    data class OnEditClick(val memoryId: String?) : MemoryDetailsScreenEvent
    data object OnBackClick : MemoryDetailsScreenEvent
    data object OnDeleteMemoryClick : MemoryDetailsScreenEvent
    data class OnConfirmDelete(val memoryId: String?) : MemoryDetailsScreenEvent
    data object OnDismissDeleteDialog : MemoryDetailsScreenEvent
}