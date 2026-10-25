package com.migwi.diary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DiaryEntry::class], version = 1, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun dao(): DiaryDao

    companion object {
        @Volatile
        private var instance: DiaryDatabase? = null

        fun get(context: Context): DiaryDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "diary.db",
                ).build().also { instance = it }
            }
        }
    }
}
