package com.example.habitzen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitzen.data.db.HabitEntity
import com.example.habitzen.domain.repository.HabitRepository
import com.example.habitzen.domain.usecase.GetHabitsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
            repository.insertHabit(HabitEntity(name = name))
        }
    }

    fun deleteHabit(habit: HabitEntity){
        viewModelScope.launch{
            repository.deleteHabit(habit)
        }
    }
}