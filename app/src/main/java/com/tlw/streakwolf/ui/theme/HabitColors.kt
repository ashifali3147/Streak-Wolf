package com.tlw.streakwolf.ui.theme

import androidx.compose.ui.graphics.Color
import com.tlw.streakwolf.domain.model.HabitColor

private val FlameLight = Color(0xFFBA7517)
private val FlameDark = Color(0xFFEF9F27)

private val ClayLight = Color(0xFFB4453A)
private val ClayDark = Color(0xFFE08476)

private val InkLight = Color(0xFF3A6392)
private val InkDark = Color(0xFF7BA5D4)

private val TealLight = Color(0xFF2F7A72)
private val TealDark = Color(0xFF6FBFB4)

private val PlumLight = Color(0xFF7A4A80)
private val PlumDark = Color(0xFFC08FC6)

private val SlateLight = Color(0xFF5E6B6E)
private val SlateDark = Color(0xFF9BAAAD)

fun HabitColor.resolve(darkTheme: Boolean): Color = when (this) {
    HabitColor.FLAME -> if (darkTheme) FlameDark else FlameLight
    HabitColor.CLAY -> if (darkTheme) ClayDark else ClayLight
    HabitColor.INK -> if (darkTheme) InkDark else InkLight
    HabitColor.TEAL -> if (darkTheme) TealDark else TealLight
    HabitColor.PLUM -> if (darkTheme) PlumDark else PlumLight
    HabitColor.SLATE -> if (darkTheme) SlateDark else SlateLight
}
