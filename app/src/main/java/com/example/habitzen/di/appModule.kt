package com.example.habitzen.di

import androidx.room.Room
import com.example.habitzen.data.db.AppDatabase
import com.example.habitzen.data.repository.HabitRepositoryImpl
import com.example.habitzen.domain.repository.HabitRepository
import com.example.habitzen.domain.usecase.GetHabitsUseCase
import com.example.habitzen.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
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

    // Репозиторий
    single<HabitRepository> { HabitRepositoryImpl(get()) }

    // Use Case
    single { GetHabitsUseCase(get()) }

    // ViewModel
    viewModel { HomeViewModel(get()) }
}