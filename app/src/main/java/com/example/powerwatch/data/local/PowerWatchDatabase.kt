
package com.example.powerwatch.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ReportEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PowerWatchDatabase : RoomDatabase() {

    abstract fun reportDao(): ReportDao

    companion object {
        @Volatile
        private var INSTANCE: PowerWatchDatabase? = null

        fun getDatabase(context: Context): PowerWatchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PowerWatchDatabase::class.java,
                    "powerwatch_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
