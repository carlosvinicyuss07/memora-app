package com.example.memoraapp.ui.screens.memories

sealed interface MemoriesScreenEvent {
    data object OnInit : MemoriesScreenEvent
    data class OnMemoryClick(val id: Int) : MemoriesScreenEvent
    data object OnAddMemoryClick : MemoriesScreenEvent
    data object OnBackClick : MemoriesScreenEvent
    data object OnRefresh : MemoriesScreenEvent
}
