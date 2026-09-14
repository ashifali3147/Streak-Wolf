package com.tlw.streakwolf.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tlw.streakwolf.ui.theme.StreakWolfTheme
import com.tlw.streakwolf.ui.theme.body
import com.tlw.streakwolf.ui.theme.title

/**
 * Nothing-here-yet placeholder. Deliberately knows nothing about any UiState so the
 * archived list and habit detail can use it too — it takes strings, not a screen's state.
 *
 * [actionLabel] and [onAction] are optional: an empty state with no next action just
 * explains itself.
 */
@Composable
fun EmptyState(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.title,
            textAlign = TextAlign.Center,
        )
        Text(
            text = message,
            color = StreakWolfTheme.colors.onSurfaceMuted,
            style = MaterialTheme.typography.body,
            textAlign = TextAlign.Center,
        )
        if (actionLabel != null && onAction != null) {
            TextButton(onClick = onAction) { Text(actionLabel) }
        }
    }
}

@Preview(name = "Empty · dark")
@Composable
private fun EmptyStateDarkPreview() {
    StreakWolfTheme(darkTheme = true) {
        EmptyState(
            title = "No habits yet",
            message = "Tap New habit to start your first streak.",
        )
    }
}

@Preview(name = "Empty · with action")
@Composable
private fun EmptyStateActionPreview() {
    StreakWolfTheme(darkTheme = false) {
        EmptyState(
            title = "Nothing archived",
            message = "Habits you archive show up here instead of being deleted.",
            actionLabel = "Back to today",
            onAction = {},
        )
    }
}
