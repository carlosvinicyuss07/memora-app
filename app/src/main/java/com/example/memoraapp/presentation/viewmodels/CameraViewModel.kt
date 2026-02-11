package com.example.memoraapp.presentation.viewmodels

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.presentation.ui.screens.camera.CameraEvent
import com.example.memoraapp.presentation.ui.screens.camera.CameraSideEffect
import com.example.memoraapp.presentation.ui.screens.camera.CameraState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    var lensFacing by mutableStateOf(CameraSelector.LENS_FACING_BACK)

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    private val _sideEffects = Channel<CameraSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    fun onEvent(event: CameraEvent) {
        when (event) {
            CameraEvent.OnSwitchCamera -> {
                lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK)
                    CameraSelector.LENS_FACING_FRONT
                else
                    CameraSelector.LENS_FACING_BACK
            }

            is CameraEvent.OnPhotoCaptured -> {
                viewModelScope.launch {
                    _sideEffects.send(
                        CameraSideEffect.ReturnPhoto(
                            uri = event.uri
                        )
                    )
                }
            }
        }
    }
}