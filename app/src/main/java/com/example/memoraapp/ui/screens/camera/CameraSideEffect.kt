package com.example.memoraapp.ui.screens.camera

import android.net.Uri

sealed interface CameraSideEffect {
    data class ReturnPhoto(val uri: String, val returnRoute: String) : CameraSideEffect
    data class ShowError(val message: String) : CameraSideEffect
}