package com.example.memoraapp.di

import androidx.room.Room
import com.example.memoraapp.data.local.AppDatabase
import com.example.memoraapp.data.local.MIGRATION_1_2
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "memora.db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    single {
        get<AppDatabase>().memoryDao()
    }

}
