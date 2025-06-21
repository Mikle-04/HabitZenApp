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
import com.example.habitzen.presentation.HistoryViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavHostController,
    viewModel: HistoryViewModel = koinViewModel()
) {
    val history by viewModel.history.collectAsState()

    val datePickerState = rememberDatePickerState()
    var showPicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Текущая дата
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("History & Stats") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showPicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Progress Chart", style = MaterialTheme.typography.titleMedium)

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val barWidth = size.width / (history.size * 2f)
                    history.forEachIndexed { index, item ->
                        val barHeight = (size.height * (item.doneCount / 10f).coerceAtMost(1f))
                        drawRoundRect(
                            color = Color.Blue,
                            topLeft = Offset(
                                x = index * barWidth * 2 + barWidth / 2,
                                y = size.height - barHeight
                            ),
                            size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Habits for $selectedDate", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            val habitsForDate by viewModel.getHabitsByDate(selectedDate.toString()).collectAsState(
                initial = emptyList()
            )

            if (habitsForDate.isEmpty()) {
                Text("No habits done for this day.")
            } else {
                LazyColumn {
                    items(habitsForDate) {
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(it.name)
                                if (it.isDone) Text("✅")
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
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = java.time.Instant.ofEpochMilli(millis)
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDate()
                    }
                    showPicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

