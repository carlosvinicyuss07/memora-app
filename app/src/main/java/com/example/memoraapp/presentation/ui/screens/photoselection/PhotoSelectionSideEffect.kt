package com.example.memoraapp.presentation.ui.screens.photoselection

sealed interface PhotoSelectionSideEffect {
    object NavigateToCamera : PhotoSelectionSideEffect
    object NavigateToGallery : PhotoSelectionSideEffect
    object NavigateBack : PhotoSelectionSideEffect
}