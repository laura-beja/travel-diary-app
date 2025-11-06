package com.laurabejarano.traveldiary.ui.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.laurabejarano.traveldiary.data.model.TravelLog
import com.laurabejarano.traveldiary.ui.ui.components.BottomNav
import com.laurabejarano.traveldiary.ui.ui.navigation.NavRoutes
import com.laurabejarano.traveldiary.viewmodel.TravelLogViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TravelLogViewModel = viewModel() // ensure shared instance; see NavHost section
) {
    val logs by viewModel.travelLogs.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showFavouritesOnly by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Travel Diary") }
            )
                 },

        bottomBar = {
            BottomNav(navController = navController)
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(NavRoutes.Create.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add new log")
            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {

            // Filter toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Show Favourites Only", fontWeight = FontWeight.SemiBold)
                Switch(
                    checked = showFavouritesOnly,
                    onCheckedChange = { showFavouritesOnly = it }
                )
            }

            val list = if (showFavouritesOnly) logs.filter { it.isFavourite } else logs

            if (list.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No items yet.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 88.dp)
                ) {
                    items(list, key = { it.id }) { log ->
                        TravelLogRow(
                            log = log,
                            onViewDetails = {
                                viewModel.addLog(it)
                                navController.navigate("${NavRoutes.Details.route}/${log.id}")
                                //navController.navigate("details")
                            },
                            onMarkFavourite = {
                                if (!it.isFavourite) {
                                    viewModel.markAsFavourite(it)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Item added to favourites!")
                                    }
                                } else {
                                    // unfavourite with different text
                                    viewModel.toggleFavourite(it, false)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Removed from favourites.")
                                    }
                                }
                            }
                        )
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TravelLogRow(
    log: TravelLog,
    onViewDetails: (TravelLog) -> Unit,
    onMarkFavourite: (TravelLog) -> Unit
) {
    var menuOpen by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (log.isFavourite) Color(0xFFFFF4F4) else Color.Unspecified)
            .clickable { onViewDetails(log) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title
        Column(Modifier.weight(1f)) {
            Text(text = log.title, style = MaterialTheme.typography.titleMedium)
            if (log.location != null) {
                Text(
                    text = log.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Favourite badge/icon when true
        if (log.isFavourite) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favourite",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 4.dp)
            )
        }

        // 3-dot menu
        Box {
            IconButton(onClick = { menuOpen = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "More options")
            }
            DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                DropdownMenuItem(
                    text = { Text("View Details") },
                    onClick = {
                        menuOpen = false
                        onViewDetails(log)
                    }
                )
                DropdownMenuItem(
                    text = { Text(if (log.isFavourite) "Unmark Favourite" else "Mark as Favourite") },
                    onClick = {
                        menuOpen = false
                        onMarkFavourite(log)
                    }
                )
            }
        }
    }
}