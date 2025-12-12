package com.example.memoraapp.ui.screens.memories

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memoraapp.domain.viewmodels.MemoriesViewModel
import com.example.memoraapp.ui.AppRoute
import com.example.memoraapp.ui.components.buttons.CircleShapeExtendedFAB
import com.example.memoraapp.ui.components.cards.MemoryCardComponent
import com.example.memoraapp.ui.components.topbar.TopbarComponent
import com.example.memoraapp.ui.theme.MemoraAppTheme
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate

@Composable
fun MemoriesScreen(
    navController: NavController,
    viewModel: MemoriesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(MemoriesScreenEvent.OnInit)
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { effect ->
            when (effect) {
                is MemoriesScreenSideEffect.NavigateToDetail ->
                    navController.navigate(AppRoute.MemoryDetails.route) // depois comecar a mandar effect.id

                is MemoriesScreenSideEffect.NavigateToCreate ->
                    navController.navigate(AppRoute.MemoryForm.route)

                is MemoriesScreenSideEffect.NavigateToWelcome ->
                    navController.navigateUp()

                is MemoriesScreenSideEffect.ShowError ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    MemoriesScreenContent(
        state = uiState,
        onEvent = viewModel::onEvent
    )
}
@Composable
fun MemoriesScreenContent(
    state: MemoriesScreenState,
    onEvent: (MemoriesScreenEvent) -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopbarComponent(screenName = "Minhas Memórias", onBackClick = { onEvent(MemoriesScreenEvent.OnBackClick) } )
        },
        floatingActionButton = {
            CircleShapeExtendedFAB(
                icon = Icons.Filled.Add,
                contentDescription = "FAB Nova Memória",
                text = "Nova Memória",
                onClick = { onEvent(MemoriesScreenEvent.OnAddMemoryClick) }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
                .fillMaxSize()
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.memories) { memory ->
                    MemoryCardComponent(
                        memory = memory,
                        onClick = {
                            onEvent(MemoriesScreenEvent.OnMemoryClick(memory.id))
                        }
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun MemoriesScreenView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            MemoriesScreenContent(
                state = MemoriesScreenState().copy(
                    memories = listOf(
                        MemoryUi(
                            1,
                            "Pôr do Sol na Praia",
                            "teste",
                            "01-01-1999"
                        )
                    )
                )
            ) {}
        }
    }
}