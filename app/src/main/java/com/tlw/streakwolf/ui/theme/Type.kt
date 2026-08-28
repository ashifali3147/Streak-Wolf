package com.tlw.streakwolf.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Six styles, two weights (400 and 500). They live in the Material [Typography] slots
 * so stock Material components inherit them, and are read through the named accessors
 * below so call sites say what they mean.
 */
val Typography = Typography(
    displayMedium = TextStyle(          // displayStreak
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 46.sp,
        lineHeight = 52.sp,
        letterSpacing = (-0.5).sp,
    ),
    headlineSmall = TextStyle(          // headline
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(            // title
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(             // body
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(             // label
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),
    labelSmall = TextStyle(             // caption
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
    ),
)

val Typography.displayStreak: TextStyle get() = displayMedium
val Typography.headline: TextStyle get() = headlineSmall
val Typography.title: TextStyle get() = titleMedium
val Typography.body: TextStyle get() = bodyMedium
val Typography.label: TextStyle get() = labelLarge
val Typography.caption: TextStyle get() = labelSmall
