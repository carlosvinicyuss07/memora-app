package com.example.memoraapp.presentation.ui.imagepicker

import android.net.Uri

interface ImagePickerController {
    fun setSelectedImage(uri: Uri?)
    fun clear()
}