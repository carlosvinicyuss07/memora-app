package com.example.memoraapp.di

import com.example.memoraapp.data.FakeMemoryRepository
import com.example.memoraapp.domain.MemoryRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<MemoryRepository> { FakeMemoryRepository() }
}