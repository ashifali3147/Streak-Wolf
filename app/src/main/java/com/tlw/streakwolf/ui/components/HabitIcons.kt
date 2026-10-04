package com.tlw.streakwolf.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The fixed icon set a habit can be tagged with.
 *
 * Keys, not resource ids — [com.tlw.streakwolf.domain.model.Habit.iconKey] stores the
 * key, so the icon can be swapped without a migration. Order here is display order.
 *
 * Only icons from `material-icons-core` are used; the extended set is a separate
 * (large) dependency this project doesn't pull in.
 */
val HabitIcons: Map<String, ImageVector> = linkedMapOf(
    "star" to Icons.Default.Star,
    "favorite" to Icons.Default.Favorite,
    "home" to Icons.Default.Home,
    "person" to Icons.Default.Person,
    "build" to Icons.Default.Build,
    "face" to Icons.Default.Face,
    "place" to Icons.Default.Place,
    "date" to Icons.Default.DateRange,
    "play" to Icons.Default.PlayArrow,
    "thumb_up" to Icons.Default.ThumbUp,
    "search" to Icons.Default.Search,
    "cart" to Icons.Default.ShoppingCart,
)

/** What a habit gets when it was created before icons existed, or with an unknown key. */
const val DefaultHabitIconKey: String = "star"

/** Never null: an unknown or missing key falls back to the default rather than crashing. */
fun habitIconFor(key: String?): ImageVector =
    HabitIcons[key] ?: HabitIcons.getValue(DefaultHabitIconKey)
