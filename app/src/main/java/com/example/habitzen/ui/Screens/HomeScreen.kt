package com.example.habitzen.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val habits by viewModel.habits.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    var newHabitName by remember { mutableStateOf("") }

    Scaffold(
        topBar =
            {
                TopAppBar(
                    title = { Text("Today") },
                    actions = {
                        // Кнопка для перехода на экран Habits
                        IconButton(onClick = {
                            navController.navigate("habits")
                        }) {
                            Icon(Icons.Default.List, contentDescription = "All Habits")
                        }
                    }
                )
            },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog = true
            }) {
                Text("+")
            }
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (habits.isEmpty()) {
                Text(
                    text = "No habits yet!",
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
                        HabitItem(
                            name = habit.name,
                            isDone = habit.isDone,
                            onCheckedChange = { isChecked ->
                                viewModel.update(habit, isChecked)
                            }
                        )

                    }
                }
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        newHabitName = ""
                    },
                    title = { Text("New Habit") },
                    text = {
                        TextField(
                            value = newHabitName,
                            onValueChange = { newHabitName = it },
                            placeholder = { Text("Enter habit name") }
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            // Добавляем новую привычку через ViewModel
                            if (newHabitName.isNotBlank()) {
                                viewModel.addHabit(newHabitName.trim())
                                newHabitName = ""
                                showDialog = false
                            }
                        }) {
                            Text("Add")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDialog = false
                            newHabitName = ""
                        }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun HabitItem(
    name: String,
    isDone: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Чекбокс для отметки выполнена ли привычка
            Checkbox(
                checked = isDone,
                onCheckedChange = onCheckedChange
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Название привычки
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


