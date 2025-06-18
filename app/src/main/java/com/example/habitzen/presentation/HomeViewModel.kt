package com.example.habitzen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitzen.domain.usecase.GetHabitsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val getHabitsUseCase: GetHabitsUseCase
) : ViewModel(){

    val habits = getHabitsUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}