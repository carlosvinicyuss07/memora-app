package com.example.memoraapp.di

import com.example.memoraapp.domain.viewmodels.FormMemoryViewModel
import com.example.memoraapp.domain.viewmodels.MemoriesViewModel
import com.example.memoraapp.domain.viewmodels.MemoryDetailsViewModel
import com.example.memoraapp.domain.viewmodels.PhotoSelectionViewModel
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
            repository = get()
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

}