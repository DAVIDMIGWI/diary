package com.migwi.diary.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM entries ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<DiaryEntry>>

    @Query("SELECT * FROM entries WHERE id = :id")
    suspend fun get(id: Long): DiaryEntry?

    @Insert
    suspend fun insert(entry: DiaryEntry): Long

    @Update
    suspend fun update(entry: DiaryEntry)

    @Query("DELETE FROM entries WHERE id = :id")
    suspend fun delete(id: Long)
}
