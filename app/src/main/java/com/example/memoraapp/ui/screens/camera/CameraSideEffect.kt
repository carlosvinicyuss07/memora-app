package com.example.memoraapp.ui.screens.camera

import android.net.Uri

sealed interface CameraSideEffect {
    data class ReturnPhoto(val uri: Uri) : CameraSideEffect
    data class ShowError(val message: String) : CameraSideEffect
}