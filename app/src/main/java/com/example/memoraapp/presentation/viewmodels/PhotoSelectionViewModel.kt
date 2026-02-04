package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoraapp.presentation.ui.screens.photoselection.PhotoSelectionScreenEvent
import com.example.memoraapp.presentation.ui.screens.photoselection.PhotoSelectionSideEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PhotoSelectionViewModel : ViewModel() {

    private val _effects =  Channel<PhotoSelectionSideEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    fun onEvent(event: PhotoSelectionScreenEvent) {
        when (event) {
            PhotoSelectionScreenEvent.OnClickCamera ->
                sendEffect(PhotoSelectionSideEffect.NavigateToCamera)

            PhotoSelectionScreenEvent.OnClickGallery ->
                sendEffect(PhotoSelectionSideEffect.NavigateToGallery)

            PhotoSelectionScreenEvent.OnBack ->
                sendEffect(PhotoSelectionSideEffect.NavigateBack)
        }
    }

    fun sendEffect(effect: PhotoSelectionSideEffect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }

}