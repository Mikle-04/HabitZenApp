package com.example.habitzen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitzen.data.db.HabitEntity
import com.example.habitzen.domain.repository.HabitRepository
import com.example.habitzen.domain.usecase.GetHabitsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeViewModel(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val repository: HabitRepository
) : ViewModel(){

    val habits = getHabitsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun update(habit: HabitEntity, isDone: Boolean){
        viewModelScope.launch {
            repository.updateHabit(habit.copy(isDone = isDone))
        }
    }

    fun addHabit(name : String){
        viewModelScope.launch {
            val date = LocalDate.now().toString()
            repository.insertHabit(HabitEntity(name = name, date = date))
        }
    }

    fun deleteHabit(habit: HabitEntity){
        viewModelScope.launch{
            repository.deleteHabit(habit)
        }
    }
    fun editHabit(habit: HabitEntity, newName: String) {
        viewModelScope.launch {
            repository.updateHabit(
                habit.copy(name = newName)
            )
        }
    }
}