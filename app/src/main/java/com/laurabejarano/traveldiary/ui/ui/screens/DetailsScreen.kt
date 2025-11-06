// DetailsScreen.kt
package com.laurabejarano.traveldiary.ui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laurabejarano.traveldiary.viewmodel.TravelLogViewModel
import com.laurabejarano.traveldiary.ui.ui.navigation.NavRoutes
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: TravelLogViewModel = viewModel(),
    id: Int?
) {
    // Load from DB when the ID arrives
    LaunchedEffect(id) {
        if (id != null) viewModel.loadLogById(id)
    }

    val log by viewModel.currentLog.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trip Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (id != null) {
                        TextButton(onClick = {
                            navController.navigate("${NavRoutes.Create.route}/$id")
                        }) { Text("Edit") }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (log == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading trip…")
            }
        } else {
            val item = log!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Title: ${item.title}", style = MaterialTheme.typography.titleLarge)
                Text("Location: ${item.location}", style = MaterialTheme.typography.bodyLarge)
                Text("Dates: ${item.startDate} – ${item.endDate}", style = MaterialTheme.typography.bodyLarge)
                Text("Participants: ${item.participants}", style = MaterialTheme.typography.bodyMedium)
                Text("Description: ${item.description}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        onClick = {
                            viewModel.deleteLog(item)
                            navController.popBackStack()
                        }
                    ) { Text("Delete") }
                }
            }
        }
    }
}
