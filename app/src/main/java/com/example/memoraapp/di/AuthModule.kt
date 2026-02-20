package com.example.memoraapp.di

import com.example.memoraapp.data.auth.AuthRepository
import com.example.memoraapp.data.repository.AuthRepositoryImplementation
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImplementation() }
}