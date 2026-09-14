package com.tlw.streakwolf.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tlw.streakwolf.ui.addedit.AddHabitSheet
import com.tlw.streakwolf.ui.components.EmptyState
import com.tlw.streakwolf.ui.components.HabitCard
import com.tlw.streakwolf.ui.components.HabitListSkeleton
import com.tlw.streakwolf.ui.theme.HairlineWidth
import com.tlw.streakwolf.ui.theme.StreakWolfTheme
import com.tlw.streakwolf.ui.theme.caption
import com.tlw.streakwolf.ui.theme.headline
import com.tlw.streakwolf.ui.theme.label
import com.tlw.streakwolf.ui.theme.title
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * Stateful half: owns the ViewModel and the add-sheet visibility, renders nothing itself.
 * Keeping it separate is what lets [HomeContent]'s previews run without a Hilt graph.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Saveable so the sheet survives rotation and process death.
    var showAddSheet by rememberSaveable { mutableStateOf(false) }

    HomeContent(
        onAddClick = { showAddSheet = true },
        modifier = modifier,
        state = state
    )

    if (showAddSheet) {
        AddHabitSheet(
            onDismiss = { showAddSheet = false },
            onSave = { name, color, iconKey -> viewModel.addHabit(name, color, iconKey) },
        )
    }
}

/**
 * Built once per composition rather than per recomposition — DateTimeFormatter is not
 * cheap to construct, and the header rebuilds on every state emission.
 */
private val dateFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("EEE, d MMMM", Locale.getDefault())

@Composable
private fun HomeContent(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: HomeUiState
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("New habit") },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Today",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headline,
                )
                Text(
                    text = state.date.format(dateFormatter),
                    color = StreakWolfTheme.colors.onSurfaceMuted,
                    style = MaterialTheme.typography.caption,
                )
            }

            when (state) {
                is HomeUiState.Content -> {
                    PackStreakCard(
                        completedToday = state.habits.count { it.isCompletedToday },
                        totalHabits = state.habits.size,
                        packStreak = state.packStreak,
                        bestPackStreak = state.bestPackStreak,
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.habits.forEach { habit ->
                            HabitCard(
                                name = habit.name,
                                currentStreak = habit.currentStreak,
                                completionTrack = habit.completionTrack,
                                isCompleted = habit.isCompletedToday,
                                onCompletedChange = {},
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }

                is HomeUiState.Empty -> EmptyState(title = "", message = "")

                is HomeUiState.Loading -> HabitListSkeleton()
            }
            // Clears the FAB.
            Box(Modifier.size(56.dp))
        }
    }
}

@Composable
private fun PackStreakCard(
    completedToday: Int,
    totalHabits: Int,
    packStreak: Int,
    bestPackStreak: Int,
    modifier: Modifier = Modifier,
) {
    val colors = StreakWolfTheme.colors
    // Step 8 swaps this for a Canvas arc with an animated sweep.
    val progress = if (totalHabits == 0) 0f else completedToday.toFloat() / totalHabits

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(HairlineWidth, MaterialTheme.colorScheme.outline),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(80.dp),
                    color = colors.flame,
                    trackColor = colors.outline,
                )
                Text(
                    text = "$completedToday/$totalHabits",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.title,
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "Pack streak",
                    color = colors.onSurfaceMuted,
                    style = MaterialTheme.typography.label,
                )
                Text(
                    text = "$packStreak days",
                    color = colors.flame,
                    style = MaterialTheme.typography.headline,
                )
                Text(
                    text = "Best $bestPackStreak",
                    color = colors.onSurfaceMuted,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

private val previewState = HomeUiState.Content(
    date = LocalDate.of(2026, 8, 15),
    habits = listOf(
        HabitUiModel(
            1,
            "Morning run",
            23,
            listOf(true, false, true, true, false, true, false),
            true
        ),
    ),
    packStreak = 18,
    bestPackStreak = 41
)

@Preview(name = "Home · dark")
@Composable
private fun HomeScreenDarkPreview() {
    StreakWolfTheme(darkTheme = true) { HomeContent(onAddClick = {}, state = previewState) }
}

@Preview(name = "Home · light")
@Composable
private fun HomeScreenLightPreview() {
    StreakWolfTheme(darkTheme = false) { HomeContent(onAddClick = {}, state = previewState) }
}
