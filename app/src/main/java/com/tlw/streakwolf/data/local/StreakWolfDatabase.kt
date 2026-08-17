package com.tlw.streakwolf.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.tlw.streakwolf.data.local.dao.CompletionDao
import com.tlw.streakwolf.data.local.dao.HabitDao
import com.tlw.streakwolf.data.local.entities.CompletionEntity
import com.tlw.streakwolf.data.local.entities.HabitEntity

@Database(
    entities = [HabitEntity::class, CompletionEntity::class],
    version = 1
)
abstract class StreakWolfDatabase: RoomDatabase() {
    companion object {
        const val STREAK_WOLF_DB = "streakwolf"
        const val HABIT_TABLE = "habits"
        const val COMPLETION_TABLE = "completions"
    }
    abstract fun habitDao(): HabitDao
    abstract fun completionDao(): CompletionDao
}