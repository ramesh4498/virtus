package com.virtus.demo.hits.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.virtus.demo.hits.data.entities.Hits
import com.virtus.demo.hits.utils.TimestampConverter

@Database(entities = [Hits::class], version = 1, exportSchema = false)
@TypeConverters(TimestampConverter ::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hitsListDao(): HitsListDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "hits")
                .fallbackToDestructiveMigration()
                .build()
    }

}