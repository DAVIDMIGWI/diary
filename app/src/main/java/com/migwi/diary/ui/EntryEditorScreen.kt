package com.migwi.diary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.migwi.diary.DiaryViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EntryEditorScreen(
    entryId: Long?,
    viewModel: DiaryViewModel,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var mood by remember { mutableStateOf(Mood.Calm) }
    var confirmDelete by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(entryId) {
        val existing = entryId?.let { viewModel.load(it) } ?: return@LaunchedEffect
        title = existing.title
        body = existing.body
        mood = Mood.from(existing.mood)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.semantics { contentDescription = "Back" },
            ) {
                Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
            }
            Row {
                if (entryId != null) {
                    IconButton(
                        onClick = { confirmDelete = true },
                        modifier = Modifier.semantics { contentDescription = "Delete entry" },
                    ) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Delete entry")
                    }
                }
                TextButton(
                    onClick = {
                        if (title.isBlank() && body.isBlank()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Write a title or a few lines first.")
                            }
                            return@TextButton
                        }
                        viewModel.save(entryId, title, body, mood, onBack)
                    },
                    modifier = Modifier.semantics { contentDescription = "Save entry" },
                ) {
                    Text("Save")
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
        Text(
            text = if (entryId == null) "New entry" else "Edit entry",
            style = MaterialTheme.typography.headlineSmall,
        )

        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Mood.entries.forEach { option ->
                FilterChip(
                    selected = mood == option,
                    onClick = { mood = option },
                    label = { Text(option.label) },
                    modifier = Modifier.semantics { contentDescription = "Mood ${option.label}" },
                )
            }
        }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Title" },
            label = { Text("Title") },
            singleLine = true,
        )
        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = "Entry" },
            label = { Text("What happened") },
            minLines = 8,
        )
        }
    }

    if (confirmDelete && entryId != null) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Delete this entry?") },
            text = { Text("This page will be removed from the diary.") },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.delete(entryId, onBack) },
                    modifier = Modifier.semantics { contentDescription = "Confirm delete" },
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) {
                    Text("Keep")
                }
            },
        )
    }
}
