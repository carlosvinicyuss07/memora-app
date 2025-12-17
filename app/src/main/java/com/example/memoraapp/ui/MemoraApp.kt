package com.example.memoraapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.memoraapp.domain.Memory
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
            ) {
                val id = it.arguments?.getInt("memoryId")
                FormMemoryScreen(
                    navController = navController, memoryId = id
                )
            }

            composable(AppRoute.PhotoSource.route) {
                PhotoSelectionScreen()
            }

            composable(AppRoute.MemoryDetails.route) {
                // Apenas de exemplo, isso será substituído para passar apenas o navController

                val memory: Memory = Memory(
                    id = 1,
                    title = "Viagem Inesquecível a Fernando de Noronha",
                    description = "Fernando de Noronha é um paraíso intocado, um lugar onde a natureza mostra sua força e beleza. As águas cristalinas, as praias de areia dourada e a rica vida marinha fazem deste um dos destinos mais espetaculares do Brasil.",
                    date = LocalDate.now()
                )

                MemoryDetailsScreen(
                    memory = memory,
                    onEditClik = { navController.navigate("memoryForm/${memory.id}") },
                    onDeleteClick = { navController.navigateUp() },
                    onBack = { navController.navigateUp() }
                )
            }

        }
    }
}

sealed class AppRoute(val route: String) {
    object Welcome : AppRoute("welcome")
    object Memories : AppRoute("memories")
    object MemoryForm : AppRoute("memoryForm")
    object MemoryFormEdit : AppRoute("memoryForm/{memoryId}")
    object PhotoSource : AppRoute("photoSource")
    object MemoryDetails : AppRoute("memoryDetails")
}