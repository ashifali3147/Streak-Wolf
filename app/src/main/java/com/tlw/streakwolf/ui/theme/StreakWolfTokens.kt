package com.tlw.streakwolf.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

/**
 * The colour roles Material has no slot for. Standard roles (background, surface,
 * onSurface, outline) stay on the [androidx.compose.material3.ColorScheme].
 */
@Immutable
data class StreakWolfColors(
    val flame: Color,
    val flameSurface: Color,
    val complete: Color,
    val completeSurface: Color,
    val onSurfaceMuted: Color,
    val outline: Color,
    val background: Color,
) {
    /**
     * Heatmap intensity: [level] 0 is an empty cell, 1..4 are [complete] at
     * 25 / 50 / 75 / 100 % composited over the page. Five steps, no more.
     */
    fun heatmapCell(level: Int): Color = when (level.coerceIn(0, 4)) {
        0 -> outline
        else -> complete.copy(alpha = level.coerceIn(1, 4) * 0.25f).compositeOver(background)
    }
}

internal val LightStreakWolfColors = StreakWolfColors(
    flame = FlameLight,
    flameSurface = FlameSurfaceLight,
    complete = CompleteLight,
    completeSurface = CompleteSurfaceLight,
    onSurfaceMuted = OnSurfaceMutedLight,
    outline = OutlineLight,
    background = BackgroundLight,
)

internal val DarkStreakWolfColors = StreakWolfColors(
    flame = FlameDark,
    flameSurface = FlameSurfaceDark,
    complete = CompleteDark,
    completeSurface = CompleteSurfaceDark,
    onSurfaceMuted = OnSurfaceMutedDark,
    outline = OutlineDark,
    background = BackgroundDark,
)

internal val LocalStreakWolfColors = staticCompositionLocalOf { LightStreakWolfColors }

internal val LocalIsDarkTheme = staticCompositionLocalOf { false }
