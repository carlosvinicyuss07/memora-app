package com.example.memoraapp.di

import com.example.memoraapp.data.auth.AuthRepository
import com.example.memoraapp.data.repository.AuthRepositoryImplementation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val firebaseModule = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }

    single<AuthRepository> {
        AuthRepositoryImplementation(
            auth = get(),
            firestore = get()
        )
    }

}