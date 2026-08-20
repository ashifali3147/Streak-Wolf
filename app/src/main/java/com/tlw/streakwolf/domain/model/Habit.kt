package com.tlw.streakwolf.domain.model

import java.time.LocalDate

data class Habit(
    val id: Long = 0,
    val name: String,
    val color: HabitColor,
    val createdAt: LocalDate,
    val sortOrder: Int,
    val iconKey: String? = null,
    val archived: Boolean = false,
)
