package com.example.memoraapp.ui.screens.photoselection

sealed interface PhotoSelectionSideEffect {
    object NavigateToCamera : PhotoSelectionSideEffect
    object NavigateToGallery : PhotoSelectionSideEffect
    object NavigateBack : PhotoSelectionSideEffect
}