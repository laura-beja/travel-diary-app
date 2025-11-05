package com.laurabejarano.traveldiary.ui.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.laurabejarano.traveldiary.data.model.TravelLog
import com.laurabejarano.traveldiary.ui.ui.navigation.NavRoutes
import com.laurabejarano.traveldiary.viewmodel.TravelLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TravelLogViewModel = viewModel()
) {
    val logs by viewModel.travelLogs.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Travel Diary") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavRoutes.Create.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add new log")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(logs.size) { index ->
                val log = logs[index]
                TravelLogItem(log) {
                    // When a user taps a log, navigate to details screen
                    navController.navigate(NavRoutes.Details.route)
                }
            }
        }
    }
}

@Composable
fun TravelLogItem(log: TravelLog, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(text = log.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = log.location, style = MaterialTheme.typography.bodyMedium)
        }
    }
}