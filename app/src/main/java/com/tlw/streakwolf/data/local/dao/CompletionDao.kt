package com.tlw.streakwolf.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import com.tlw.streakwolf.data.local.StreakWolfDatabase
import com.tlw.streakwolf.data.local.entities.CompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCompletion(data: CompletionEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllCompletion(vararg data: CompletionEntity)

    @Delete
    suspend fun deleteCompletion(data: CompletionEntity)

    @Query("DELETE FROM ${StreakWolfDatabase.COMPLETION_TABLE} WHERE habitId = (:id) AND date = (:date)")
    suspend fun deleteCompletionByHabit(id: Long, date: Long)

    @Query("SELECT * FROM ${StreakWolfDatabase.COMPLETION_TABLE}")
    fun getAllCompletion(): Flow<List<CompletionEntity>>

    @Query("SELECT * FROM ${StreakWolfDatabase.COMPLETION_TABLE} WHERE id = (:id)")
    fun getCompletionById(id: Long): Flow<CompletionEntity?>

    @Query("SELECT * FROM ${StreakWolfDatabase.COMPLETION_TABLE} WHERE habitId = (:id) ORDER BY date ASC")
    fun getCompletionByHabitId(id: Long): Flow<List<CompletionEntity>>

    @Query("SELECT habitId FROM ${StreakWolfDatabase.COMPLETION_TABLE} WHERE date = (:date)")
    fun getHabitIdsCompletedOn(date: Long): Flow<List<Long>>
}