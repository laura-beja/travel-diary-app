package com.laurabejarano.traveldiary.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "travel_logs")
data class TravelLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,
    val imageUri: String?, // stored as a URI string (from gallery)
    val location: String,
    val startDate: String,
    val endDate: String,
    val participants: String, // stored as a ',' separated list
    val description: String
)