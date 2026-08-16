package com.tlw.streakwolf.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import com.tlw.streakwolf.data.local.entities.CompletionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionDao {
    @Upsert
    suspend fun addCompletion(data: CompletionEntity)

    @Upsert
    suspend fun addAllCompletion(vararg data: CompletionEntity)

    @Delete
    suspend fun deleteCompletion(data: CompletionEntity)

    @Query("DELETE FROM completions WHERE habitId = (:id) AND date = (:date)")
    suspend fun deleteCompletionByHabit(id: Long, date: Long)

    @Query("SELECT * FROM completions")
    fun getAllCompletion(): Flow<List<CompletionEntity>>

    @Query("SELECT * FROM completions WHERE id = (:id)")
    fun getCompletionById(id: Long): Flow<CompletionEntity?>

    @Query("SELECT * FROM completions WHERE habitId = (:id)")
    fun getCompletionByHabitId(id: Long): Flow<List<CompletionEntity>>
}