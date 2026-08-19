package com.tlw.streakwolf.data.repository

import com.tlw.streakwolf.data.local.entities.HabitEntity
import com.tlw.streakwolf.domain.model.Habit
import com.tlw.streakwolf.domain.model.HabitColor
import java.time.LocalDate

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        colorKey = color.name,
        createdAt = createdAt.toEpochDay(),
        sortOrder = sortOrder,
        iconKey = iconKey,
        archived = archived,
    )
}

fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = id,
        name = name,
        color = colorKey.toHabitColor(),
        createdAt = LocalDate.ofEpochDay(createdAt),
        sortOrder = sortOrder,
        iconKey = iconKey,
        archived = archived,
    )
}

private fun String.toHabitColor(): HabitColor {
    return HabitColor.entries.firstOrNull { it.name == this } ?: HabitColor.SLATE
}
