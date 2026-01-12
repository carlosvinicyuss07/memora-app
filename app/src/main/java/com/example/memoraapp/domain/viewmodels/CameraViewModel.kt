package com.example.memoraapp.domain.viewmodels

import androidx.camera.core.CameraSelector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.ui.screens.camera.CameraEvent
import com.example.memoraapp.ui.screens.camera.CameraSideEffect
import com.example.memoraapp.ui.screens.camera.CameraState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraViewModel : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    val state = _state.asStateFlow()

    private val _sideEffects = Channel<CameraSideEffect>()
    val sideEffects = _sideEffects.receiveAsFlow()

    fun onEvent(event: CameraEvent) {
        when (event) {
            CameraEvent.OnSwitchCamera -> {
                val newLens =
                    if (_state.value.lensFacing == CameraSelector.LENS_FACING_BACK)
                        CameraSelector.LENS_FACING_FRONT
                    else
                        CameraSelector.LENS_FACING_BACK

                _state.update {
                    it.copy(lensFacing = newLens)
                }
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