package com.example.memoraapp.domain.viewmodels

import androidx.camera.core.CameraSelector
import androidx.lifecycle.ViewModel
import com.example.memoraapp.ui.screens.camera.CameraEvent
import com.example.memoraapp.ui.screens.camera.CameraState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CameraViewModel : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    fun onEvent(event: CameraEvent) {
        when (event) {
            CameraEvent.SwitchCamera -> {
                _state.update {
                    it.copy(
                        lensFacing =
                            if (it.lensFacing == CameraSelector.LENS_FACING_BACK)
                                CameraSelector.LENS_FACING_FRONT
                            else
                                CameraSelector.LENS_FACING_BACK)
                }
            }
        }
    }
}