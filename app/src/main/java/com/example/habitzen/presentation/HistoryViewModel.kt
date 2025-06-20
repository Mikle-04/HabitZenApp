package com.example.habitzen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitzen.domain.usecase.GetHistoryUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(
    getHistoryUseCase: GetHistoryUseCase
) : ViewModel() {

    val history = getHistoryUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}