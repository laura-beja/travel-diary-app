package com.laurabejarano.traveldiary.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.laurabejarano.traveldiary.data.database.TravelLogDatabase
import com.laurabejarano.traveldiary.data.model.TravelLog
import com.laurabejarano.traveldiary.data.repo.TravelLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TravelLogViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TravelLogRepository
    val travelLogs: StateFlow<List<TravelLog>>

    init {
        val dao = TravelLogDatabase.getDatabase(application).travelLogDao()
        repository = TravelLogRepository(dao)
        travelLogs = repository.getAllLogs()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun addLog(log: TravelLog) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(log) }

    fun deleteLog(log: TravelLog) =
        viewModelScope.launch(Dispatchers.IO) { repository.delete(log) }

    fun searchLogs(query: String): Flow<List<TravelLog>> = repository.searchLogs(query)
}
