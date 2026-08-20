package com.tlw.streakwolf.data.repository

import com.tlw.streakwolf.data.local.entities.CompletionEntity
import com.tlw.streakwolf.domain.model.Completion
import java.time.LocalDate

fun Completion.toEntity(): CompletionEntity {
    return CompletionEntity(
        id = id,
        habitId = habitId,
        date = date.toEpochDay()
    )
}

fun CompletionEntity.toDomain(): Completion {
    return Completion(
        id = id,
        habitId = habitId,
        date = LocalDate.ofEpochDay(date)
    )
}