package com.example.habitzen.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habitzen.domain.models.HistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<HabitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits WHERE date = :date")
    fun getHabitsByDate(date: String): Flow<List<HabitEntity>>

    @Query("SELECT date, COUNT(*) as doneCount FROM habits WHERE isDone = 1 GROUP BY date ORDER BY date DESC")
    fun getHistory(): Flow<List<HistoryItem>>
}