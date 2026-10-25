package com.migwi.diary.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.migwi.diary.DiaryViewModel
import com.migwi.diary.data.DiaryEntry
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun EntryListScreen(
    viewModel: DiaryViewModel,
    onOpen: (Long) -> Unit,
    onCreate: () -> Unit,
    onPrivacy: () -> Unit,
) {
    val entries by viewModel.entries.collectAsStateWithLifecycle()

    BoxWithFab(onCreate = onCreate) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 28.dp, bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text("Diary", style = MaterialTheme.typography.headlineLarge)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (entries.isEmpty()) {
                            "A quiet place for the day."
                        } else {
                            "${entries.size} ${if (entries.size == 1) "entry" else "entries"}"
                        },
                        color = Muted,
                    )
                    TextButton(
                        onClick = onPrivacy,
                        modifier = Modifier.semantics { contentDescription = "Privacy" },
                    ) {
                        Text("Privacy")
                    }
                }
            }
            if (entries.isEmpty()) {
                item { EmptyDiary() }
            } else {
                items(entries, key = { it.id }) { entry ->
                    EntryCard(entry = entry, onClick = { onOpen(entry.id) })
                }
            }
        }
    }
}

@Composable
private fun BoxWithFab(onCreate: () -> Unit, content: @Composable () -> Unit) {
    androidx.compose.foundation.layout.Box(modifier = Modifier.fillMaxSize()) {
        content()
        FloatingActionButton(
            onClick = onCreate,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .semantics { contentDescription = "New entry" },
            containerColor = Accent,
            contentColor = androidx.compose.ui.graphics.Color.White,
        ) {
            Icon(Icons.Outlined.Edit, contentDescription = "New entry")
        }
    }
}

@Composable
private fun EmptyDiary() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Card),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Nothing written yet", style = MaterialTheme.typography.titleMedium)
            Text(
                "Tap the pen to keep the first page. Titles, a mood, and whatever you want to remember.",
                color = Muted,
            )
        }
    }
}

@Composable
private fun EntryCard(entry: DiaryEntry, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Card),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics { contentDescription = "Open ${entry.title}" },
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(Mood.from(entry.mood).label, color = Accent, style = MaterialTheme.typography.labelLarge)
                Text(entry.updatedAt.toDisplayTime(), color = Muted, style = MaterialTheme.typography.labelLarge)
            }
            Text(entry.title, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            if (entry.body.isNotBlank()) {
                Text(
                    entry.body,
                    color = Muted,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

private val dayFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEE, MMM d · h:mm a")

private fun Long.toDisplayTime(): String =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).format(dayFormatter)
