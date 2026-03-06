package com.example.memoraapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.memoraapp.presentation.ui.screens.auth.login.LoginScreen
import com.example.memoraapp.presentation.ui.screens.auth.signup.SignUpScreen
import com.example.memoraapp.presentation.viewmodels.ImagePickerViewModel
import com.example.memoraapp.presentation.ui.screens.camera.CameraScreen
import com.example.memoraapp.presentation.ui.screens.details.MemoryDetailsScreen
import com.example.memoraapp.presentation.ui.screens.form.FormMemoryScreen
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreen
import com.example.memoraapp.presentation.ui.screens.photoselection.PhotoSelectionScreen
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeScreen
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeScreenContent
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemoraApp() {
    val navController = rememberNavController()

    val imagePickerViewModel: ImagePickerViewModel = koinViewModel()

    val currentUser = FirebaseAuth.getInstance().currentUser

    val startDestination = if (currentUser != null) {
        AppRoute.MainGraph
    } else {
        AppRoute.AuthGraph
    }

    MemoraAppTheme {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            // Auth Graph
            navigation<AppRoute.AuthGraph>(
                startDestination = AppRoute.Login
            ) {
                composable<AppRoute.Login> {
                    LoginScreen(navController = navController)
                }

                composable<AppRoute.SignUp> {
                    SignUpScreen(navController = navController)
                }
            }

            // Main Graph
            navigation<AppRoute.MainGraph>(
                startDestination = AppRoute.Welcome
            ) {
                composable<AppRoute.Welcome> {
                    WelcomeScreen(
                        navController = navController
                    )
                }

                composable<AppRoute.Memories> {
                    MemoriesScreen(
                        navController = navController,
                        imagePickerViewModel = imagePickerViewModel
                    )
                }

                navigation<AppRoute.MemoryFormGraph>(
                    startDestination = AppRoute.MemoryForm
                ) {

                    composable<AppRoute.MemoryForm> {

                        FormMemoryScreen(
                            navController = navController,
                            memoryId = null,
                            imagePickerViewModel = imagePickerViewModel
                        )
                    }

                    composable<AppRoute.MemoryFormEdit> { backStackEntry ->
                        val args = backStackEntry.toRoute<AppRoute.MemoryFormEdit>()
                        FormMemoryScreen(
                            navController = navController,
                            memoryId = args.memoryId,
                            imagePickerViewModel = imagePickerViewModel
                        )
                    }
                }

                composable<AppRoute.PhotoSource> {
                    PhotoSelectionScreen(
                        navController = navController,
                        imagePickerViewModel = imagePickerViewModel
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
                    CameraScreen(
                        navController = navController,
                        imagePickerViewModel = imagePickerViewModel
                    )
                }
            }
        }
    }
}

@Serializable
sealed class AppRoute {
    @Serializable
    data object AuthGraph

    @Serializable
    data object Login

    @Serializable
    data object SignUp

    @Serializable
    data object MainGraph

    @Serializable
    data object Welcome

    @Serializable
    data object Memories

    @Serializable
    data class MemoryDetails(val memoryId: String?)

    @Serializable
    data object MemoryFormGraph

    @Serializable
    data object MemoryForm

    @Serializable
    data class MemoryFormEdit(val memoryId: String?)

    @Serializable
    data object PhotoSource

    @Serializable
    data object Camera
}