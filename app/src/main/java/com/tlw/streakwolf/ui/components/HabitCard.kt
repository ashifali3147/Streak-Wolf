package com.tlw.streakwolf.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tlw.streakwolf.ui.theme.HairlineWidth
import com.tlw.streakwolf.ui.theme.StreakWolfTheme
import com.tlw.streakwolf.ui.theme.body
import com.tlw.streakwolf.ui.theme.caption

@Composable
fun HabitCard(
    name: String,
    currentStreak: Int,
    completionTrack: List<Boolean>,
    isCompleted: Boolean,
    onCompletedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
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
                Text(
                    text = name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.body,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${currentStreak}d",
                        color = StreakWolfTheme.colors.onSurfaceMuted,
                        style = MaterialTheme.typography.caption,
                    )
                    CompletionWeekTrack(completionTrack)
                }
            }
            HabitCheckButton(
                isCompleted = isCompleted,
                onCompletedChange = onCompletedChange,
            )
        }
    }
}

@Composable
fun HabitCheckButton(
    isCompleted: Boolean,
    onCompletedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedIconToggleButton(
        checked = isCompleted,
        onCheckedChange = onCompletedChange,
        modifier = modifier.size(44.dp),
        shape = CircleShape,
        border = if (isCompleted) null else BorderStroke(HairlineWidth, MaterialTheme.colorScheme.outline),
        colors = IconButtonDefaults.outlinedIconToggleButtonColors(
            contentColor = MaterialTheme.colorScheme.outline,
            checkedContainerColor = StreakWolfTheme.colors.complete,
            checkedContentColor = MaterialTheme.colorScheme.onSecondary,
        ),
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = if (isCompleted) "Mark as not done" else "Mark as done",
        )
    }
}

@Preview
@Composable
private fun HabitCardPreview() {
    StreakWolfTheme {
        HabitCard(
            name = "Morning run",
            currentStreak = 23,
            completionTrack = listOf(true, true, true, true, true, false, true),
            isCompleted = true,
            onCompletedChange = {},
        )
    }
}
