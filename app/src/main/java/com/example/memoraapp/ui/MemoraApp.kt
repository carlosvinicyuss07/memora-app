package com.example.memoraapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.ui.screens.camera.CameraScreen
import com.example.memoraapp.ui.screens.memories.MemoriesScreen
import com.example.memoraapp.ui.screens.details.MemoryDetailsScreen
import com.example.memoraapp.ui.screens.form.FormMemoryScreen
import com.example.memoraapp.ui.screens.photoselection.PhotoSelectionScreen
import com.example.memoraapp.ui.screens.welcome.WelcomeScreen
import com.example.memoraapp.ui.theme.MemoraAppTheme
import java.time.LocalDate

@Composable
fun MemoraApp() {
    val navController = rememberNavController()

    MemoraAppTheme {
        NavHost(navController = navController, startDestination = AppRoute.Welcome.route) {
            composable(AppRoute.Welcome.route) {
                WelcomeScreen(
                    onStartClick = { navController.navigate(AppRoute.Memories.route) }
                )
            }

            composable(AppRoute.Memories.route) {
                MemoriesScreen(
                    navController = navController
                )
            }

            composable(AppRoute.MemoryForm.route) {
                FormMemoryScreen(
                    navController = navController, memoryId = null
                )
            }

            composable(
                route = AppRoute.MemoryFormEdit.route,
                arguments = listOf(navArgument("memoryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("memoryId")
                FormMemoryScreen(
                    navController = navController, memoryId = id
                )
            }

            composable(
                route = AppRoute.PhotoSource.route,
                arguments = listOf(
                    navArgument("returnRoute") {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) { navBackStackEntry ->

                PhotoSelectionScreen(
                    navController = navController,
                    returnRoute = navBackStackEntry.arguments!!.getString("returnRoute")!!
                )
            }

            composable(
                route = AppRoute.MemoryDetails.route,
                arguments = listOf(navArgument("memoryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("memoryId")

                if (id == null) return@composable

                MemoryDetailsScreen(
                    navController = navController, memoryId = id
                )
            }

            composable(AppRoute.Camera.route) {
                CameraScreen(navController = navController)
            }

        }
    }
}

sealed class AppRoute(val route: String) {
    object Welcome : AppRoute("welcome")
    object Memories : AppRoute("memories")
    object MemoryForm : AppRoute("memoryForm")
    object MemoryFormEdit : AppRoute("memoryForm/{memoryId}") {
        fun createRoute(memoryId: Int?): String {
            return "memoryForm/$memoryId"
        }
    }
    object PhotoSource : AppRoute("photoSource?returnRoute={returnRoute}") {
        fun create(returnRoute: String) =
            "photoSource?returnRoute=$returnRoute"
    }
    object Camera : AppRoute("camera?returnRoute={returnRoute}") {
        fun create(returnRoute: String) =
            "camera?returnRoute=$returnRoute"
    }
    object Gallery : AppRoute("galery") //TODO: Implementar depois
    object MemoryDetails : AppRoute("memoryDetails/{memoryId}") {
        fun createRoute(memoryId: Int?): String {
            return "memoryDetails/$memoryId"
        }
    }
}