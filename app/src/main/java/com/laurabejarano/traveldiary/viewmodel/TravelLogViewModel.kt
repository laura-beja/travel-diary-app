// TravelLogViewModel.kt
package com.laurabejarano.traveldiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.laurabejarano.traveldiary.data.database.TravelLogDB
import com.laurabejarano.traveldiary.data.model.TravelLog
import com.laurabejarano.traveldiary.data.repo.TravelLogRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TravelLogViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = TravelLogRepo(
        TravelLogDB.getDatabase(application).travelLogDao()
    )

    // List for Home/Search
    val travelLogs: StateFlow<List<TravelLog>> =
        repo.getAllLogs().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Currently loaded item (for Details and Edit)
    private val _currentLog = MutableStateFlow<TravelLog?>(null)
    val currentLog: StateFlow<TravelLog?> = _currentLog.asStateFlow()

    // Load a log from DB by ID
    fun loadLogById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentLog.value = repo.getById(id)
        }
    }

    // Insert new
    fun addLog(log: TravelLog) {
        viewModelScope.launch(Dispatchers.IO) { repo.insert(log) }
    }

    // Update existing
    fun updateLog(log: TravelLog) {
        viewModelScope.launch(Dispatchers.IO) { repo.update(log) }
    }

    // Delete existing
    fun deleteLog(log: TravelLog) {
        viewModelScope.launch(Dispatchers.IO) { repo.delete(log) }
    }

    fun markAsFavourite(log: TravelLog) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.setFavourite(log.id, true)
            // Also update selected item if it is the same
            if (_currentLog.value?.id == log.id) {
                _currentLog.update { it?.copy(isFavourite = true) }
            }
        }
    }
    fun toggleFavourite(log: TravelLog, setTo: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.setFavourite(log.id, setTo)
            if (_currentLog.value?.id == log.id) {
                _currentLog.update { it?.copy(isFavourite = setTo) }
            }
        }
    }

}
