package com.example.memoraapp.ui.screens.camera

sealed interface CameraSideEffect {
    data class ReturnPhoto(val uri: String) : CameraSideEffect
    data class ShowError(val message: String) : CameraSideEffect
}