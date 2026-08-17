package com.tlw.streakwolf.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import com.tlw.streakwolf.data.local.StreakWolfDatabase
import com.tlw.streakwolf.data.local.entities.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Upsert
    suspend fun addHabit(habit: HabitEntity)

    @Upsert
    suspend fun addHabitAll(vararg habit: HabitEntity)

    @Delete
    suspend fun deleteHabit(habit: HabitEntity)

    @Query("SELECT * FROM ${StreakWolfDatabase.HABIT_TABLE} WHERE archived = 0 ORDER BY sortOrder")
    fun getAllHabit(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM ${StreakWolfDatabase.HABIT_TABLE} WHERE archived = 1")
    fun getArchivedHabit(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM ${StreakWolfDatabase.HABIT_TABLE} WHERE id = (:id)")
    fun getHabitById(id: Long): Flow<HabitEntity?>
}