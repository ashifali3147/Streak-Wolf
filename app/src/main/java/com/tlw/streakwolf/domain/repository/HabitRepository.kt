package com.tlw.streakwolf.domain.repository

import com.tlw.streakwolf.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitRepository {
    suspend fun createNewHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

    suspend fun archiveHabit(habitId: Long)

    suspend fun removeArchiveHabit(habitId: Long)

    suspend fun markHabitComplete(habitId: Long, date: LocalDate)

    suspend fun unMarkHabitComplete(habitId: Long, date: LocalDate)

    fun getArchivedHabit(): Flow<List<Habit>>

    fun getAllHabit(): Flow<List<Habit>>

    fun getHabitById(habitId: Long): Flow<Habit?>

    fun getCompletionsForHabit(habitId: Long): Flow<List<LocalDate>>

    fun getCompletionsOn(date: LocalDate): Flow<Set<Long>>
}
