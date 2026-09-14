package com.tlw.streakwolf.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlw.streakwolf.domain.model.Habit
import com.tlw.streakwolf.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {
    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.createNewHabit(habit)
        }
    }

    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            repository.createNewHabit(habit)
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun archiveHabit(id: Long) {
        viewModelScope.launch {
            repository.archiveHabit(id)
        }
    }
}
