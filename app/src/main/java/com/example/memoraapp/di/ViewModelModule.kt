package com.example.memoraapp.di

import com.example.memoraapp.domain.viewmodels.MemoriesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MemoriesViewModel(
            repository = get()
        )
    }

}