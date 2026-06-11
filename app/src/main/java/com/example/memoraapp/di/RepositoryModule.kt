package com.example.memoraapp.di

import com.example.memoraapp.data.repository.FirestoreMemoryRepository
import com.example.memoraapp.data.repository.UserRepositoryImplementation
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.domain.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<MemoryRepository> {
        FirestoreMemoryRepository(
            firestore = get(),
            auth = get(),
            storage = get()
        )
    }
}