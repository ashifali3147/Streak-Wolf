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
import com.tlw.streakwolf.ui.components.HabitCard
import com.tlw.streakwolf.ui.theme.HairlineWidth
import com.tlw.streakwolf.ui.theme.StreakWolfTheme
import com.tlw.streakwolf.ui.theme.caption
import com.tlw.streakwolf.ui.theme.headline
import com.tlw.streakwolf.ui.theme.label
import com.tlw.streakwolf.ui.theme.title

// Step 5 is static UI only. Step 6 replaces this with HomeUiState from HomeViewModel.
private data class FakeHabit(
    val name: String,
    val currentStreak: Int,
    val completionTrack: List<Boolean>,
    val isCompleted: Boolean,
)

private val fakeHabits = listOf(
    FakeHabit("Morning run", 23, listOf(true, true, true, true, true, false, true), true),
    FakeHabit("Read 20 pages", 7, listOf(true, false, true, true, true, false, true), true),
    FakeHabit("No screens after 10", 4, listOf(false, true, true, false, true, true, false), false),
    FakeHabit("Stretch", 12, listOf(true, true, false, true, true, true, true), false),
)

/**
 * Stateful half: owns the ViewModel and the add-sheet visibility, renders nothing itself.
 * Keeping it separate is what lets [HomeContent]'s previews run without a Hilt graph.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    // TODO(step 6): feed this into HomeContent and delete fakeHabits.
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Saveable so the sheet survives rotation and process death.
    var showAddSheet by rememberSaveable { mutableStateOf(false) }

    HomeContent(
        onAddClick = { showAddSheet = true },
        modifier = modifier,
    )

    if (showAddSheet) {
        AddHabitSheet(
            onDismiss = { showAddSheet = false },
            onSave = { name, color, iconKey -> viewModel.addHabit(name, color, iconKey) },
        )
    }
}

@Composable
private fun HomeContent(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val completedToday = fakeHabits.count { it.isCompleted }

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
                    text = "Sat, 15 August",
                    color = StreakWolfTheme.colors.onSurfaceMuted,
                    style = MaterialTheme.typography.caption,
                )
            }

            PackStreakCard(
                completedToday = completedToday,
                totalHabits = fakeHabits.size,
                packStreak = 18,
                bestPackStreak = 41,
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                fakeHabits.forEach { habit ->
                    HabitCard(
                        name = habit.name,
                        currentStreak = habit.currentStreak,
                        completionTrack = habit.completionTrack,
                        isCompleted = habit.isCompleted,
                        onCompletedChange = {},
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
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

@Preview(name = "Home · dark")
@Composable
private fun HomeScreenDarkPreview() {
    StreakWolfTheme(darkTheme = true) { HomeContent(onAddClick = {}) }
}

@Preview(name = "Home · light")
@Composable
private fun HomeScreenLightPreview() {
    StreakWolfTheme(darkTheme = false) { HomeContent(onAddClick = {}) }
}
