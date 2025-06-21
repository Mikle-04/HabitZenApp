package com.example.habitzen.data.repository

import com.example.habitzen.data.db.HabitDao
import com.example.habitzen.data.db.HabitEntity
import com.example.habitzen.domain.models.HistoryItem
import com.example.habitzen.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow

class HabitRepositoryImpl(private val habitDao: HabitDao) : HabitRepository{
    override fun getHabits(): Flow<List<HabitEntity>> {
        return habitDao.getAllHabits()
    }

    override suspend fun insertHabit(habit: HabitEntity) {
        habitDao.insertHabit(habit)
    }

    override suspend fun updateHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    override suspend fun deleteHabit(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }
    override fun getHistory(): Flow<List<HistoryItem>> = habitDao.getHistory()
}