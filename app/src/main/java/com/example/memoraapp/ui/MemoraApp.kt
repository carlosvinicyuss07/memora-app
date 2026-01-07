package com.example.memoraapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.memoraapp.ui.screens.camera.CameraScreen
import com.example.memoraapp.ui.screens.memories.MemoriesScreen
import com.example.memoraapp.ui.screens.details.MemoryDetailsScreen
import com.example.memoraapp.ui.screens.form.FormMemoryScreen
import com.example.memoraapp.ui.screens.photoselection.PhotoSelectionScreen
import com.example.memoraapp.ui.screens.welcome.WelcomeScreen
import com.example.memoraapp.ui.theme.MemoraAppTheme
import kotlinx.serialization.Serializable

@Composable
fun MemoraApp() {
    val navController = rememberNavController()

    MemoraAppTheme {
        NavHost(navController = navController, startDestination = AppRoute.Welcome) {
            composable<AppRoute.Welcome> {
                WelcomeScreen(
                    onStartClick = { navController.navigate(AppRoute.Memories) }
                )
            }

            composable<AppRoute.Memories> {
                MemoriesScreen(
                    navController = navController
                )
            }

            composable<AppRoute.MemoryForm> {
                FormMemoryScreen(
                    navController = navController, memoryId = null
                )
            }

            composable<AppRoute.MemoryFormEdit> { backStackEntry ->
                val args = backStackEntry.toRoute<AppRoute.MemoryFormEdit>()
                FormMemoryScreen(
                    navController = navController, memoryId = args.memoryId
                )
            }

            composable<AppRoute.PhotoSource> {
                PhotoSelectionScreen(
                    navController = navController
                )
            }

            composable<AppRoute.MemoryDetails> { backStackEntry ->
                val args = backStackEntry.toRoute<AppRoute.MemoryDetails>()

                if (args.memoryId == null) return@composable

                MemoryDetailsScreen(
                    navController = navController, memoryId = args.memoryId
                )
            }

            composable<AppRoute.Camera> {
                CameraScreen(navController = navController)
            }

        }
    }
}

@Serializable
sealed class AppRoute() {
    @Serializable
    data object Welcome

    @Serializable
    data object Memories

    @Serializable
    data class MemoryDetails(val memoryId: Int?)

    @Serializable
    data object MemoryForm

    @Serializable
    data class MemoryFormEdit(val memoryId: Int?)

    @Serializable
    data object PhotoSource

    @Serializable
    data object Camera

    @Serializable
    data object Gallery //TODO: Implementar depois
}