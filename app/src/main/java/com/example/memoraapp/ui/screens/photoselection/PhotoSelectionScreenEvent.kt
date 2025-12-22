package com.example.memoraapp.ui.screens.photoselection

sealed interface PhotoSelectionScreenEvent {
    object OnClickCamera : PhotoSelectionScreenEvent
    object OnClickGallery : PhotoSelectionScreenEvent
    object OnBack : PhotoSelectionScreenEvent
}