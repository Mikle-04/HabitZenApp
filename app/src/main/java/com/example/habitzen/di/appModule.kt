package com.example.habitzen.di

import androidx.room.Room
import com.example.habitzen.data.db.AppDatabase
import org.koin.dsl.module

val appModule = module {

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "habit_db"
        ).fallbackToDestructiveMigration().build()
    }

    //Dao
    single { get<AppDatabase>().habitDao() }
}