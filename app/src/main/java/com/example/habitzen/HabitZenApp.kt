package com.example.habitzen

import android.app.Application
import com.example.habitzen.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HabitZenApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@HabitZenApp)
            modules(appModule)
        }
    }
}