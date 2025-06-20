package com.example.habitzen.domain.usecase

import com.example.habitzen.domain.repository.HabitRepository

class GetHistoryUseCase(private val repository: HabitRepository) {
    operator fun invoke() = repository.getHistory()
}