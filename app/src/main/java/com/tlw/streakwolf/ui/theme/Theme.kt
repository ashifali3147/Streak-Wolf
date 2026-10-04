package com.tlw.streakwolf.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val LightColorScheme = lightColorScheme(
    primary = FlameLight,
    onPrimary = SurfaceLight,
    primaryContainer = FlameSurfaceLight,
    onPrimaryContainer = FlameLight,
    secondary = CompleteLight,
    onSecondary = SurfaceLight,
    secondaryContainer = CompleteSurfaceLight,
    onSecondaryContainer = CompleteLight,
    background = BackgroundLight,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = BackgroundLight,
    onSurfaceVariant = OnSurfaceMutedLight,
    outline = OutlineLight,
    outlineVariant = OutlineLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = FlameDark,
    onPrimary = BackgroundDark,
    primaryContainer = FlameSurfaceDark,
    onPrimaryContainer = FlameDark,
    secondary = CompleteDark,
    onSecondary = BackgroundDark,
    secondaryContainer = CompleteSurfaceDark,
    onSecondaryContainer = CompleteDark,
    background = BackgroundDark,
    onBackground = OnSurfaceDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = OnSurfaceMutedDark,
    outline = OutlineDark,
    outlineVariant = OutlineDark,
)

/**
 * No dynamic colour on purpose: one accent carries meaning here, and letting the
 * wallpaper repaint `flame` would take that meaning with it.
 */
@Composable
fun StreakWolfTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val streakWolfColors = if (darkTheme) DarkStreakWolfColors else LightStreakWolfColors

    CompositionLocalProvider(
        LocalStreakWolfColors provides streakWolfColors,
        LocalIsDarkTheme provides darkTheme,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

/** `StreakWolfTheme.colors.flame`, alongside `MaterialTheme.colorScheme` / `.typography`. */
object StreakWolfTheme {
    val colors: StreakWolfColors
        @Composable @ReadOnlyComposable get() = LocalStreakWolfColors.current

    val isDark: Boolean
        @Composable @ReadOnlyComposable get() = LocalIsDarkTheme.current
}
