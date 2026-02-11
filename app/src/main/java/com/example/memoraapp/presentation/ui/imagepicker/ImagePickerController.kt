package com.example.memoraapp.presentation.ui.imagepicker

interface ImagePickerController {
    fun setSelectedImage(uri: String?)
    fun clear()
}