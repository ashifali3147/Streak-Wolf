package com.tlw.streakwolf.data.local.entities

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.tlw.streakwolf.data.local.StreakWolfDatabase

@Entity(
    tableName = StreakWolfDatabase.HABIT_TABLE
)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val colorKey: String,
    val createdAt: Long,
    val sortOrder: Int,
    val iconKey: String? = null,
    val archived: Boolean = false,
)
