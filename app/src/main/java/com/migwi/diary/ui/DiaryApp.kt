package com.migwi.diary.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.migwi.diary.DiaryViewModel

@Composable
fun DiaryApp(viewModel: DiaryViewModel = viewModel()) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = Paper,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            NavHost(navController = navController, startDestination = "entries") {
                composable("entries") {
                    EntryListScreen(
                        viewModel = viewModel,
                        onOpen = { id -> navController.navigate("editor?id=$id") },
                        onCreate = { navController.navigate("editor?id=-1") },
                        onPrivacy = { navController.navigate("privacy") },
                    )
                }
                composable("privacy") {
                    PrivacyScreen(onBack = { navController.popBackStack() })
                }
                composable(
                    route = "editor?id={id}",
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.LongType
                            defaultValue = -1L
                        },
                    ),
                ) { entry ->
                    val id = entry.arguments?.getLong("id") ?: -1L
                    EntryEditorScreen(
                        entryId = id.takeIf { it > 0 },
                        viewModel = viewModel,
                        snackbarHostState = snackbarHostState,
                        onBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}
