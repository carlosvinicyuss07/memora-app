package com.example.memoraapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.memoraapp.ui.screens.memories.MemoriesScreen
import com.example.memoraapp.ui.screens.memories.MemoriesViewModel
import com.example.memoraapp.ui.screens.welcome.WelcomeScreen
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun MemoraApp() {
    val navController = rememberNavController()

    MemoraAppTheme {
        NavHost(navController = navController, startDestination = "welcome") {
            composable("welcome") {
                WelcomeScreen(
                    onStartClick = { navController.navigate("memories") }
                )
            }

            composable("memories") {
                MemoriesScreen(
                    viewModel = viewModel { MemoriesViewModel() },
                    onNewMemoryClick = { navController.navigate("welcome") }
                )
            }

        }
    }
}

sealed class AppRoute(val route: String) {
    object Welcome : AppRoute("welcome")
    object Memories : AppRoute("memories")
    object MemoryForm : AppRoute("memoryForm")
    object PhotoSource : AppRoute("photoSource")
}