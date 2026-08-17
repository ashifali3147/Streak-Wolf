package com.tlw.streakwolf.domain.model

import java.time.LocalDate

data class Completion(
    val id: Long,
    val habitId: Long,
    val date: LocalDate,
)
