// CreateLogScreen.kt
package com.laurabejarano.traveldiary.ui.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.laurabejarano.traveldiary.data.model.TravelLog
import com.laurabejarano.traveldiary.viewmodel.TravelLogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLogScreen(
    navController: NavController,
    viewModel: TravelLogViewModel = viewModel(),
    editingId: Int? // ✅ NEW: null = create, non-null = edit
) {
    val context = LocalContext.current

    // Prefill when editing
    LaunchedEffect(editingId) {
        if (editingId != null) viewModel.loadLogById(editingId)
    }
    val editingLog by viewModel.currentLog.collectAsState()

    // Inputs
    var title by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }
    var participants by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    // When editingLog arrives, prefill once
    LaunchedEffect(editingLog) {
        editingLog?.let { log ->
            title = log.title
            location = log.location
            startDate = log.startDate
            endDate = log.endDate
            participants = log.participants ?: ""
            description = log.description ?: ""
            imageUri = log.imageUri?.let { Uri.parse(it) }
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> imageUri = uri }
    )

    val isEditing = editingId != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Travel Log" else "New Travel Log") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank()) {
                            val log = TravelLog(
                                id = editingId ?: 0, // Room will ignore id=0 on insert
                                title = title,
                                imageUri = imageUri?.toString(),
                                location = location,
                                startDate = startDate,
                                endDate = endDate,
                                participants = participants,
                                description = description
                            )
                            if (isEditing) {
                                viewModel.updateLog(log)
                            } else {
                                viewModel.addLog(log)
                            }
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Trip Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Start Date") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text("End Date") },
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = participants,
                onValueChange = { participants = it },
                label = { Text("Participants (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Selected Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    OutlinedButton(onClick = { pickImageLauncher.launch("image/*") }) {
                        Text("Select Image from Gallery")
                    }
                }
            }
        }
    }
}
