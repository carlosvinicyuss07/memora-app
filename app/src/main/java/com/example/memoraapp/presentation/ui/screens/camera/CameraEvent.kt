package com.example.memoraapp.presentation.ui.screens.camera

import android.net.Uri

sealed interface CameraEvent {
    object OnSwitchCamera : CameraEvent
    data class OnPhotoCaptured(val uri: String) : CameraEvent
}