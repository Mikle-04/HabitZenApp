package com.example.habitzen.ui.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.habitzen.presentation.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.habitzen.data.db.HabitEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitsScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val habits by viewModel.habits.collectAsState()

    var habitToEdit by remember { mutableStateOf<HabitEntity?>(null) }

    var newName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All Habits") },
                navigationIcon = {
                    // Кнопка Назад
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (habits.isEmpty()) {
                Text(
                    text = "No habits found! 📭",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(habits) { habit ->
                        HabitRow(
                            habit = habit,
                            onDelete = { viewModel.deleteHabit(habit) },
                            onEdit = {
                                habitToEdit = habit
                                newName = habit.name
                            }
                        )
                    }
                }
            }
            habitToEdit?.let { habit ->
                AlertDialog(
                    onDismissRequest = {
                        habitToEdit = null
                        newName = ""
                    },
                    title = { Text("Edit Habit") },
                    text = {
                        TextField(
                            value = newName,
                            onValueChange = { newName = it },
                            placeholder = { Text("Habit name") }
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            if (newName.isNotBlank()) {
                                // Вызываем ViewModel для обновления привычки
                                viewModel.editHabit(habit, newName.trim())
                                habitToEdit = null
                                newName = ""
                            }
                        }) {
                            Text("Update")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            habitToEdit = null
                            newName = ""
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

/**
 * Элемент списка привычек с кнопкой Удалить.
 */
@Composable
fun HabitRow(
    habit: HabitEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() } // Клик по карточке = редактировать
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

