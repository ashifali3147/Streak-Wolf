package com.tlw.streakwolf.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlw.streakwolf.domain.model.Habit
import com.tlw.streakwolf.domain.model.HabitColor
import com.tlw.streakwolf.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        repository.getAllHabit().map { HomeUiState.Loading }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000), HomeUiState.Loading
        )

    /**
     * Builds the habit from what the add sheet collects. `createdAt` and `sortOrder`
     * are decided here, not in the form: a new habit goes to the end of the list.
     */
    fun addHabit(name: String, color: HabitColor, iconKey: String) {
        viewModelScope.launch {
            val existing = repository.getAllHabit().first()
            repository.createNewHabit(
                Habit(
                    name = name,
                    color = color,
                    createdAt = LocalDate.now(),
                    sortOrder = existing.size,
                    iconKey = iconKey,
                )
            )
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
