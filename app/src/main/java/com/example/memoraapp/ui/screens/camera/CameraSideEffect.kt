package com.example.memoraapp.ui.screens.camera

import com.example.memoraapp.ui.util.UiText

sealed interface CameraSideEffect {
    data class ReturnPhoto(val uri: String) : CameraSideEffect
    data class ShowError(val message: UiText) : CameraSideEffect
}