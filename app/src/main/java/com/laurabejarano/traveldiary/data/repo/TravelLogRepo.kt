package com.laurabejarano.traveldiary.data.repo

import com.laurabejarano.traveldiary.data.dao.TravelLogDao
import com.laurabejarano.traveldiary.data.model.TravelLog
import kotlinx.coroutines.flow.Flow

class TravelLogRepository(private val dao: TravelLogDao) {
    fun getAllLogs(): Flow<List<TravelLog>> = dao.getAllLogs()
    fun searchLogs(query: String): Flow<List<TravelLog>> = dao.search(query)
    suspend fun insert(log: TravelLog) = dao.insert(log)
    suspend fun update(log: TravelLog) = dao.update(log)
    suspend fun delete(log: TravelLog) = dao.delete(log)
}