package com.example.memoraapp.presentation.viewmodels

import com.example.memoraapp.presentation.ui.imagepicker.ImagePickerController

class FakeImagePickerController : ImagePickerController {
    var wasCleared = false

    override fun setSelectedImage(uri: String?) {}

    override fun clear() {
        wasCleared = true
    }
}