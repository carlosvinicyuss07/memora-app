package com.example.memoraapp.ui.screens.camera

import android.net.Uri
import androidx.camera.core.CameraSelector
import com.example.memoraapp.ui.AppRoute

data class CameraState(
    val imageUri: Uri? = null,
    val returnRoute: String = AppRoute.MemoryForm.route,
    val lensFacing: Int = CameraSelector.LENS_FACING_FRONT
)
