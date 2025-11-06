package com.laurabejarano.traveldiary.data.dao

import androidx.room.*
import com.laurabejarano.traveldiary.data.model.TravelLog
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelLogDao {

    // Insert a new travel log
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(travelLog: TravelLog)

    // Update an existing travel log
    @Update
    suspend fun updateLog(travelLog: TravelLog)

    // Delete a travel log
    @Delete
    suspend fun deleteLog(travelLog: TravelLog)

    // Get all travel logs (for Home screen)
    @Query("SELECT * FROM travel_logs ORDER BY id DESC")
    fun getAllLogs(): Flow<List<TravelLog>>

    // Get a single log by ID (for Details screen)
    @Query("SELECT * FROM travel_logs WHERE id = :logId")
    suspend fun getLogById(logId: Int): TravelLog?

    // Search by title or location (for Search screen)
    @Query("SELECT * FROM travel_logs WHERE title LIKE '%' || :query || '%' OR location LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchLogs(query: String): Flow<List<TravelLog>>

    // Add as Favorite
    @Query("UPDATE travel_logs SET isFavourite = :favourite WHERE id = :id")
    suspend fun setFavourite(id: Int, favourite: Boolean)
}