package com.example.habitzen.domain.repository

import com.example.habitzen.data.db.HabitEntity
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getHabits(): Flow<List<HabitEntity>>
    suspend fun insertHabit(habit: HabitEntity)
    suspend fun updateHabit(habit: HabitEntity)
    suspend fun deleteHabit(habit: HabitEntity)
}