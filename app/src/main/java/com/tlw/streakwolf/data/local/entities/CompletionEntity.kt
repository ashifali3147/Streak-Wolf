package com.tlw.streakwolf.data.local.entities

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.tlw.streakwolf.data.local.StreakWolfDatabase

@Entity(
    tableName = StreakWolfDatabase.COMPLETION_TABLE,
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("habitId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
        )
    ],
    indices = [Index(value = ["habitId", "date"], unique = true)]
)
data class CompletionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val habitId: Long,
    val date: Long,
)
