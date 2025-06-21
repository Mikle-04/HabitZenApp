package com.example.habitzen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitzen.domain.repository.HabitRepository
import com.example.habitzen.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(
    private val getHistoryUseCase: GetHistoryUseCase,
    private val repository: HabitRepository
) : ViewModel() {

    val history = getHistoryUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getHabitsByDate(date: String) = repository.getHabitsByDate(date)
}