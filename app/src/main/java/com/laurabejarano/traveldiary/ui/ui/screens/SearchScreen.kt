package com.laurabejarano.traveldiary.ui.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.laurabejarano.traveldiary.data.model.TravelLog
import com.laurabejarano.traveldiary.ui.ui.components.BottomNav
import com.laurabejarano.traveldiary.ui.ui.navigation.NavRoutes
import com.laurabejarano.traveldiary.viewmodel.TravelLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TravelLogViewModel = viewModel()
) {
    val allLogs by viewModel.travelLogs.collectAsState()
    var query by remember { mutableStateOf(TextFieldValue("")) }

    // Filter logs based on query
    val filteredLogs = remember(query, allLogs) {
        if (query.text.isBlank()) allLogs
        else allLogs.filter {
            it.title.contains(query.text, ignoreCase = true) ||
                    it.location.contains(query.text, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Search Trips") })
        },
        bottomBar = {
            BottomNav(navController = navController)
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // 🔍 Search Bar
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search by title or location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            // 🧭 Results List
            if (filteredLogs.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No matching travel logs found")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredLogs) { log ->
                        SearchResultItem(
                            log = log,
                            onClick = {
                                navController.navigate("${NavRoutes.Details.route}/${log.id}")
                            }
                        )
                    }
                }
            }
        }
    } // ✅ Scaffold closes here
}


// Card for displaying each search result
@Composable
fun SearchResultItem(
    log: TravelLog,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = log.title, style = MaterialTheme.typography.titleMedium)
            Text(text = log.location, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
