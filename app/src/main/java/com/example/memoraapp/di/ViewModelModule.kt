package com.example.memoraapp.di

import com.example.memoraapp.presentation.viewmodels.CameraViewModel
import com.example.memoraapp.presentation.viewmodels.FormMemoryViewModel
import com.example.memoraapp.presentation.viewmodels.ImagePickerViewModel
import com.example.memoraapp.presentation.viewmodels.MemoriesViewModel
import com.example.memoraapp.presentation.viewmodels.MemoryDetailsViewModel
import com.example.memoraapp.presentation.viewmodels.PhotoSelectionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MemoriesViewModel(
            repository = get()
        )
    }

    viewModel {
        FormMemoryViewModel(
            repository = get(),
            imagePickerViewModel = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        MemoryDetailsViewModel(
            repository = get()
        )
    }

    viewModel {
        PhotoSelectionViewModel()
    }

    viewModel {
        CameraViewModel()
    }

    viewModel {
        ImagePickerViewModel()
    }

}