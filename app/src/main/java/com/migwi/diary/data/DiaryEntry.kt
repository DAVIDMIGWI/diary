package com.migwi.diary.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val body: String,
    val mood: String,
    val createdAt: Long,
    val updatedAt: Long,
)
