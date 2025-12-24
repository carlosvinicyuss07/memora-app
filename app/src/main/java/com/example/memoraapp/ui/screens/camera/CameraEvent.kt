package com.example.memoraapp.ui.screens.camera

sealed interface CameraEvent {
    data object SwitchCamera : CameraEvent
}