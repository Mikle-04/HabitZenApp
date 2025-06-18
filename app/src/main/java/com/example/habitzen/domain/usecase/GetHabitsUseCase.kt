package com.example.habitzen.domain.usecase

import com.example.habitzen.domain.repository.HabitRepository

class GetHabitsUseCase(private val repository: HabitRepository) {

    operator fun invoke() = repository.getHabits()
}