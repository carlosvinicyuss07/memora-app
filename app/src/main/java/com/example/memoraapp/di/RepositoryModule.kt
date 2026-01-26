package com.example.memoraapp.di

import com.example.memoraapp.data.repository.RoomMemoryRepository
import com.example.memoraapp.domain.MemoryRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MemoryRepository> {
        RoomMemoryRepository(
            dao = get()
        )
    }
}