package com.example.habitzen.ui.Screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.habitzen.domain.models.HistoryItem
import com.example.habitzen.presentation.HistoryViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val history by viewModel.history.collectAsState()

    // Выбранная дата
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val datePickerState = rememberDatePickerState()
    var showPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("История и статистика") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = { showPicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Выбрать дату")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (history.isEmpty()) {
                item {
                    Text("История пока пуста.")
                }
            } else {
                items(history) { item ->
                    HistoryProgressItem(item)
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Привычки на $selectedDate",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                val habitsForDate by viewModel.getHabitsByDate(selectedDate.toString())
                    .collectAsState(initial = emptyList())

                if (habitsForDate.isEmpty()) {
                    Text("На выбранную дату привычек нет.")
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        habitsForDate.forEach {
                            Card(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(it.name)
                                    if (it.isDone) Text("✅ Выполнено")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showPicker = false
                }) {
                    Text("ОК")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) {
                    Text("Отмена")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun HistoryProgressItem(item: HistoryItem) {
    Column {
        Text(
            text = item.date,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = if (item.totalCount == 0) 0f else item.doneCount.toFloat() / item.totalCount,
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = Color.LightGray
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "${item.doneCount} из ${item.totalCount} выполнено",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
