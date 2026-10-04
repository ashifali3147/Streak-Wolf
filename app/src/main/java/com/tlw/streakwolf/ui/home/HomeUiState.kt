package com.tlw.streakwolf.ui.home

import java.time.LocalDate

sealed interface HomeUiState {
    /**
     * The day the screen is showing. On the interface because the header renders it in
     * every state — including [Loading], which is why that one isn't a `data object`.
     * The clock belongs to the ViewModel, so even "loading" is loading *a* date.
     */
    val date: LocalDate

    data class Loading(override val date: LocalDate) : HomeUiState

    data class Empty(override val date: LocalDate) : HomeUiState

    data class Content(
        override val date: LocalDate,
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
