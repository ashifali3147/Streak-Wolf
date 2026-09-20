package com.tlw.streakwolf.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlw.streakwolf.domain.model.Habit
import com.tlw.streakwolf.domain.model.HabitColor
import com.tlw.streakwolf.domain.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {

    /**
     * Read once, not per emission: calling LocalDate.now() inside the mapping would
     * re-evaluate on every database change. Swap this for an injected Clock when the
     * streak tests need to fake "today".
     */
    private val today: LocalDate = LocalDate.now()

    val uiState: StateFlow<HomeUiState> =
    // Combined, not just mapped: Room re-emits a flow only when *its* table changes,
        // so toggling a completion never touches the habits flow on its own.
        combine(
            repository.getAllHabit(),
            repository.getCompletionsOn(today),
        ) { habits, completedIds ->
            if (habits.isEmpty()) {
                HomeUiState.Empty(today)
            } else {
                // Streaks are 0 until the streak logic (step 7) lands.
                HomeUiState.Content(
                    date = today,
                    habits = habits.map { it.toUiModel(completedIds) },
                    packStreak = 0,
                    bestPackStreak = 0,
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000), HomeUiState.Loading(today)
        )

    /**
     * Streak and track are still placeholders: they need the completion history from
     * getCompletionsBetween, which lands with the streak logic.
     */
    private fun Habit.toUiModel(completedToday: Set<Long>) = HabitUiModel(
        id = id,
        name = name,
        currentStreak = 0,
        completionTrack = List(7) { false },
        isCompletedToday = id in completedToday,
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

    fun toggleTodayHabit(id: Long, value: Boolean) {
        viewModelScope.launch {
            if (value) {
                repository.markHabitComplete(habitId = id, date = today)
            } else {
                repository.unMarkHabitComplete(habitId = id, date = today)
            }
        }
    }
}
