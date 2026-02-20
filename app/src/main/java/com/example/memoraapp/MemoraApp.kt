package com.example.memoraapp

import android.app.Application
import com.example.memoraapp.di.authModule
import com.example.memoraapp.di.databaseModule
import com.example.memoraapp.di.repositoryModule
import com.example.memoraapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MemoraApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MemoraApp)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule,
                authModule
            )
        }
    }
}