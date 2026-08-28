package com.tlw.streakwolf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tlw.streakwolf.ui.theme.StreakWolfTheme

/** The last seven days, oldest first. */
@Composable
fun CompletionWeekTrack(
    completionTrack: List<Boolean>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        completionTrack.forEach { isCompleted -> CompletionDot(isCompleted) }
    }
}

@Composable
private fun CompletionDot(isCompleted: Boolean) {
    val colors = StreakWolfTheme.colors
    Box(
        modifier = Modifier
            .size(8.dp)
            .background(
                color = if (isCompleted) colors.complete else colors.outline,
                shape = CircleShape,
            )
    )
}

@Preview
@Composable
private fun CompletionWeekTrackPreview() {
    StreakWolfTheme {
        CompletionWeekTrack(listOf(true, false, false, true, true, true, false))
    }
}
