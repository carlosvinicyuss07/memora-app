package com.example.memoraapp.ui.screens.camera

import android.net.Uri

sealed interface CameraEvent {
    object OnSwitchCamera : CameraEvent
    object OnTakePhoto : CameraEvent
    data class OnPhotoCaptured(val uri: Uri) : CameraEvent
}