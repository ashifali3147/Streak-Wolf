package com.tlw.streakwolf.ui.addedit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tlw.streakwolf.domain.model.HabitColor
import com.tlw.streakwolf.ui.components.DefaultHabitIconKey
import com.tlw.streakwolf.ui.components.HabitIcons
import com.tlw.streakwolf.ui.theme.HairlineWidth
import com.tlw.streakwolf.ui.theme.StreakWolfTheme
import com.tlw.streakwolf.ui.theme.caption
import com.tlw.streakwolf.ui.theme.label
import com.tlw.streakwolf.ui.theme.resolve
import com.tlw.streakwolf.ui.theme.title
import kotlinx.coroutines.launch

/**
 * Add / edit habit, per spec §"Add habit sheet": a [ModalBottomSheet], not a
 * navigation destination. Name, icon, colour.
 *
 * Stateful half. Owns the form state; hands a validated draft to [onSave] rather than a
 * built [com.tlw.streakwolf.domain.model.Habit], because `createdAt` and `sortOrder`
 * are the ViewModel's business, not a form's.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitSheet(
    onDismiss: () -> Unit,
    onSave: (name: String, color: HabitColor, iconKey: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    // Saveable, not just remembered: the sheet has to survive process death (spec step 10).
    // HabitColor is an enum, so the name is stored rather than the entry itself.
    var name by rememberSaveable { mutableStateOf("") }
    var colorName by rememberSaveable { mutableStateOf(HabitColor.FLAME.name) }
    var iconKey by rememberSaveable { mutableStateOf(DefaultHabitIconKey) }
    var nameError by rememberSaveable { mutableStateOf<String?>(null) }

    /** Lets the sheet slide out instead of vanishing, then tells the caller it's gone. */
    fun dismissWithAnimation() {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
    ) {
        AddHabitSheetContent(
            name = name,
            onNameChange = {
                name = it
                // Clear the error as soon as they start fixing it, not on the next tap.
                if (nameError != null) nameError = null
            },
            nameError = nameError,
            selectedColor = HabitColor.valueOf(colorName),
            onColorChange = { colorName = it.name },
            selectedIconKey = iconKey,
            onIconChange = { iconKey = it },
            onSave = {
                val trimmed = name.trim()
                if (trimmed.isEmpty()) {
                    nameError = "Give it a name"
                } else {
                    onSave(trimmed, HabitColor.valueOf(colorName), iconKey)
                    dismissWithAnimation()
                }
            },
        )
    }
}

/**
 * Stateless half — every value comes in as a parameter, every change goes out as a
 * callback. This is the one the previews and any future UI test target.
 */
@Composable
fun AddHabitSheetContent(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String?,
    selectedColor: HabitColor,
    onColorChange: (HabitColor) -> Unit,
    selectedIconKey: String,
    onIconChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val accent = selectedColor.resolve(StreakWolfTheme.isDark)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text(
            text = "New habit",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.title,
        )

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            singleLine = true,
            isError = nameError != null,
            supportingText = nameError?.let { error ->
                {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.caption,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
        )

        IconPicker(
            selectedIconKey = selectedIconKey,
            onIconChange = onIconChange,
            accent = accent,
        )

        ColorPicker(
            selectedColor = selectedColor,
            onColorChange = onColorChange,
        )

        // Spec: enabled even when the name is blank — validate on tap and show the
        // inline error, rather than leaving the user with a dead button and no reason.
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Save")
        }
    }
}

@Composable
private fun FieldLabel(text: String) {
    Text(
        text = text,
        color = StreakWolfTheme.colors.onSurfaceMuted,
        style = MaterialTheme.typography.label,
    )
}

@Composable
private fun IconPicker(
    selectedIconKey: String,
    onIconChange: (String) -> Unit,
    accent: Color,
    modifier: Modifier = Modifier,
) {
    val entries = remember { HabitIcons.entries.toList() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FieldLabel("Icon")
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            // Bounded on purpose: an unbounded lazy grid inside a Column throws.
            modifier = Modifier.heightIn(max = 120.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(entries, key = { it.key }) { (key, icon) ->
                val selected = key == selectedIconKey
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(CircleShape)
                        .background(if (selected) accent.copy(alpha = 0.14f) else Color.Transparent)
                        .border(
                            width = HairlineWidth,
                            color = if (selected) accent else MaterialTheme.colorScheme.outline,
                            shape = CircleShape,
                        )
                        .clickable { onIconChange(key) },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = key,
                        tint = if (selected) accent else StreakWolfTheme.colors.onSurfaceMuted,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun ColorPicker(
    selectedColor: HabitColor,
    onColorChange: (HabitColor) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = StreakWolfTheme.isDark

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FieldLabel("Colour")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            HabitColor.entries.forEach { habitColor ->
                val swatch = habitColor.resolve(isDark)
                val selected = habitColor == selectedColor
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(swatch)
                        .clickable { onColorChange(habitColor) },
                    contentAlignment = Alignment.Center,
                ) {
                    if (selected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.surface,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Add habit · dark")
@Composable
private fun AddHabitSheetContentDarkPreview() {
    StreakWolfTheme(darkTheme = true) {
        AddHabitSheetContent(
            name = "Morning run",
            onNameChange = {},
            nameError = null,
            selectedColor = HabitColor.FLAME,
            onColorChange = {},
            selectedIconKey = "play",
            onIconChange = {},
            onSave = {},
        )
    }
}

@Preview(name = "Add habit · blank name error")
@Composable
private fun AddHabitSheetContentErrorPreview() {
    StreakWolfTheme(darkTheme = false) {
        AddHabitSheetContent(
            name = "",
            onNameChange = {},
            nameError = "Give it a name",
            selectedColor = HabitColor.TEAL,
            onColorChange = {},
            selectedIconKey = DefaultHabitIconKey,
            onIconChange = {},
            onSave = {},
        )
    }
}
