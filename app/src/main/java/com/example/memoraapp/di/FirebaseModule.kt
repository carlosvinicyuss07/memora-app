package com.example.memoraapp.di

import com.example.memoraapp.domain.AuthRepository
import com.example.memoraapp.data.auth.repository.AuthRepositoryImplementation
import com.example.memoraapp.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.dsl.module

val firebaseModule = module {

    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }

    single {
        UserRepository(
            firestore = get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImplementation(
            auth = get(),
            firestore = get()
        )
    }

}