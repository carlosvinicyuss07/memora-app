package com.example.memoraapp.di

import com.example.memoraapp.data.repository.FakeMemoryRepository
import com.example.memoraapp.domain.MemoryRepository
import org.koin.dsl.module

// Apenas para testes
val fakeRepositoryModule = module {

    single<MemoryRepository> {
        FakeMemoryRepository()
    }
}