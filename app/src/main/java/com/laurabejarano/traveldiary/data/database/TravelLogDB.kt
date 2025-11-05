package com.laurabejarano.traveldiary.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.laurabejarano.traveldiary.data.dao.TravelLogDao
import com.laurabejarano.traveldiary.data.model.TravelLog

@Database(entities = [TravelLog::class], version = 1, exportSchema = false)
abstract class TravelLogDatabase : RoomDatabase() {
    abstract fun travelLogDao(): TravelLogDao

    companion object {
        @Volatile private var INSTANCE: TravelLogDatabase? = null

        fun getDatabase(context: Context): TravelLogDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TravelLogDatabase::class.java,
                    "travel_log_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}