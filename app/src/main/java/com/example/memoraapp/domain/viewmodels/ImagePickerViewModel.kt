package com.example.memoraapp.domain.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImagePickerViewModel : ViewModel() {

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    fun setSelectedImage(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun clear() {
        _selectedImageUri.value = null
    }
}