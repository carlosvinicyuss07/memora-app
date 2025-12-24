package com.example.memoraapp.ui.screens.camera

import androidx.camera.core.CameraSelector

data class CameraState(
    val lensFacing: Int = CameraSelector.LENS_FACING_BACK
)
