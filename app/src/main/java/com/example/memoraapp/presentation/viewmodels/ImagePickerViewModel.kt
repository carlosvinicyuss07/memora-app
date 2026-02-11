package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.memoraapp.presentation.ui.imagepicker.ImagePickerController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImagePickerViewModel : ViewModel(), ImagePickerController {

    private val _selectedImageUri = MutableStateFlow<String?>(null)
    val selectedImageUri: StateFlow<String?> = _selectedImageUri

    override fun setSelectedImage(uri: String?) {
        _selectedImageUri.value = uri
    }

    override fun clear() {
        _selectedImageUri.value = null
    }
}