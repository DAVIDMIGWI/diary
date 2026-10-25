package com.migwi.diary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrivacyScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
        }
        Text("Privacy", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Diary keeps your writing on this phone. It does not have an account, and it does not send entries anywhere.",
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            "What is stored",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            "Each entry’s title, text, mood, and the time you wrote it are saved in the app’s private storage on your device. Diary does not ask for your name, email, contacts, location, or photos.",
            color = Muted,
        )
        Text(
            "What leaves the phone",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            "Nothing you write is uploaded by Diary. The app has no ads and no analytics. Uninstalling Diary removes the entries stored by the app on that device. You can also delete a single entry from its page.",
            color = Muted,
        )
        Text(
            "Children",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            "Diary is not directed at children.",
            color = Muted,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        )
    }
}
