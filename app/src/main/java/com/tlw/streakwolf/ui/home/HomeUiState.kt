package com.tlw.streakwolf.ui.home

import java.time.LocalDate

sealed interface HomeUiState {
    data object Loading : HomeUiState

    data class Empty(val date: LocalDate) : HomeUiState

    data class Content(
        val date: LocalDate,
        val habits: List<HabitUiModel>,
        val packStreak: Int,
        val bestPackStreak: Int,
    ) : HomeUiState {
        val totalHabits: Int get() = habits.size
        val completedToday: Int get() = habits.count { it.isCompletedToday }
    }
}

data class HabitUiModel(
    val id: Long,
    val name: String,
    val currentStreak: Int,
    /** Oldest to newest, always 7 entries; the last one is today. */
    val completionTrack: List<Boolean>,
    val isCompletedToday: Boolean,
)
