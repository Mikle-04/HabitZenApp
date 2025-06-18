package com.example.habitzen.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HabitEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
}