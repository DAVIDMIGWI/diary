package com.migwi.diary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.migwi.diary.data.DiaryDatabase
import com.migwi.diary.data.DiaryEntry
import com.migwi.diary.ui.Mood
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiaryViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = DiaryDatabase.get(app).dao()

    val entries: StateFlow<List<DiaryEntry>> = dao.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun save(id: Long?, title: String, body: String, mood: Mood, onDone: () -> Unit) {
        val cleanTitle = title.trim().ifEmpty { "Untitled" }
        val cleanBody = body.trim()
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            if (id == null) {
                dao.insert(
                    DiaryEntry(
                        title = cleanTitle,
                        body = cleanBody,
                        mood = mood.name,
                        createdAt = now,
                        updatedAt = now,
                    ),
                )
            } else {
                val existing = dao.get(id) ?: return@launch
                dao.update(
                    existing.copy(
                        title = cleanTitle,
                        body = cleanBody,
                        mood = mood.name,
                        updatedAt = now,
                    ),
                )
            }
            onDone()
        }
    }

    fun delete(id: Long, onDone: () -> Unit) {
        viewModelScope.launch {
            dao.delete(id)
            onDone()
        }
    }

    suspend fun load(id: Long): DiaryEntry? = dao.get(id)
}
