package com.tlw.streakwolf.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tlw.streakwolf.ui.theme.HairlineWidth
import com.tlw.streakwolf.ui.theme.StreakWolfTheme

/**
 * Placeholder rows shaped like [HabitCard], shown while the first emission is on its way.
 *
 * Why a skeleton and not a spinner: the screen keeps its layout, so nothing jumps when
 * the real rows arrive. A spinner would also collide visually with the determinate ring
 * in the pack streak card, which already means something else.
 *
 * [shimmer] defaults to off. Room reads resolve in milliseconds, so an animated sweep
 * usually shows for a frame or two and reads as a flicker rather than as progress —
 * turn it on only if you gate this behind a delay.
 */
@Composable
fun HabitListSkeleton(
    modifier: Modifier = Modifier,
    rowCount: Int = 3,
    shimmer: Boolean = false,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(rowCount) {
            HabitCardSkeleton(shimmer = shimmer)
        }
    }
}

@Composable
private fun HabitCardSkeleton(
    shimmer: Boolean,
    modifier: Modifier = Modifier,
) {
    val brush = placeholderBrush(shimmer)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(HairlineWidth, MaterialTheme.colorScheme.outline),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Stands in for the habit name.
                Bar(width = 140.dp, height = 14.dp, brush = brush)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // The "23d" streak label.
                    Bar(width = 24.dp, height = 10.dp, brush = brush)
                    // Seven day dots, same 8.dp/4.dp rhythm as CompletionWeekTrack.
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        repeat(7) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(brush)
                            )
                        }
                    }
                }
            }
            // The check button.
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(brush)
            )
        }
    }
}

@Composable
private fun Bar(
    width: Dp,
    height: Dp,
    brush: Brush,
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(4.dp))
            .background(brush)
    )
}

/**
 * Flat fill, or a sweep that travels left to right when [shimmer] is on. The sweep is a
 * moving gradient rather than an alpha pulse so it reads as light passing over the
 * placeholder instead of the whole row blinking.
 */
@Composable
private fun placeholderBrush(shimmer: Boolean): Brush {
    val base = StreakWolfTheme.colors.outline
    if (!shimmer) return SolidBrushOf(base)

    val highlight = StreakWolfTheme.colors.onSurfaceMuted.copy(alpha = 0.25f)
    val transition = rememberInfiniteTransition(label = "skeleton")
    // Travels in pixels rather than gradient stops: stops would have to be clamped to
    // 0..1 and could collide at the ends, which a linear gradient won't accept.
    val translate by transition.animateFloat(
        initialValue = -SweepWidthPx,
        targetValue = SweepWidthPx * 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200),
            repeatMode = RepeatMode.Restart,
        ),
        label = "sweep",
    )

    return Brush.linearGradient(
        colors = listOf(base, highlight, base),
        start = Offset(translate, 0f),
        end = Offset(translate + SweepWidthPx, 0f),
    )
}

private const val SweepWidthPx = 320f

/** Brush.horizontalGradient needs two stops; a one-colour gradient is the flat case. */
private fun SolidBrushOf(color: Color): Brush =
    Brush.horizontalGradient(listOf(color, color))

@Preview(name = "Skeleton · dark")
@Composable
private fun HabitListSkeletonDarkPreview() {
    StreakWolfTheme(darkTheme = true) { HabitListSkeleton() }
}

@Preview(name = "Skeleton · shimmer")
@Composable
private fun HabitListSkeletonShimmerPreview() {
    StreakWolfTheme(darkTheme = false) { HabitListSkeleton(shimmer = true) }
}
