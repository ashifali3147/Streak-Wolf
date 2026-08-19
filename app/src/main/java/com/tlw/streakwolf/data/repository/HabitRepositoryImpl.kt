package com.tlw.streakwolf.data.repository

import com.tlw.streakwolf.data.local.dao.CompletionDao
import com.tlw.streakwolf.data.local.dao.HabitDao
import com.tlw.streakwolf.domain.model.Completion
import com.tlw.streakwolf.domain.model.Habit
import com.tlw.streakwolf.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(private val habitDao: HabitDao, private val completionDao: CompletionDao): HabitRepository {
    override suspend fun createNewHabit(habit: Habit) {
        habitDao.addHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit.toEntity())
    }

    override suspend fun archiveHabit(habitId: Long) {
        habitDao.updateArchive(habitId, true)
    }

    override suspend fun removeArchiveHabit(habitId: Long) {
        habitDao.updateArchive(habitId, false)
    }

    override suspend fun markHabitComplete(habitId: Long, date: LocalDate) {
        completionDao.addCompletion(Completion(habitId = habitId, date = date).toEntity())
    }

    override suspend fun unMarkHabitComplete(habitId: Long, date: LocalDate) {
        completionDao.deleteCompletionByHabit(habitId, date.toEpochDay())
    }

    override fun getArchivedHabit(): Flow<List<Habit>> {
        return habitDao.getArchivedHabit().map { entities -> entities.map { it.toDomain() } }
    }

    override fun getAllHabit(): Flow<List<Habit>> {
        return habitDao.getAllHabit().map { entities -> entities.map { it.toDomain() } }
    }

    override fun getHabitById(habitId: Long): Flow<Habit?> {
        return habitDao.getHabitById(habitId).map { it?.toDomain() }
    }

    override fun getCompletionsForHabit(habitId: Long): Flow<List<LocalDate>> {
        return completionDao.getCompletionByHabitId(habitId).map { entities -> entities.map { it.toDomain().date } }
    }

    override fun getCompletionsOn(date: LocalDate): Flow<Set<Long>> {
        return completionDao.getHabitIdsCompletedOn(date.toEpochDay()).map { it.toSet() }
    }
}