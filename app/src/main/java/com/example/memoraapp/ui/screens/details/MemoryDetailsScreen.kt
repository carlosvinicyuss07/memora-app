package com.example.memoraapp.ui.screens.details

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memoraapp.domain.viewmodels.MemoryDetailsViewModel
import com.example.memoraapp.ui.AppRoute
import com.example.memoraapp.ui.components.buttons.ExtendedFAB
import com.example.memoraapp.ui.components.imagelayouts.MemoryDetailsImageComponent
import com.example.memoraapp.ui.components.topbar.TopbarComponent
import com.example.memoraapp.ui.theme.MemoraAppTheme
import com.example.memoraapp.ui.util.rememberImageBitmap
import org.koin.androidx.compose.koinViewModel

@Composable
fun MemoryDetailsScreen(
    navController: NavController,
    viewModel: MemoryDetailsViewModel = koinViewModel(),
    memoryId: Int?
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onEvent(MemoryDetailsScreenEvent.OnInit(memoryId))

        viewModel.effects.collect { effect ->
            when (effect) {
                MemoryDetailsSideEffect.CloseScreen ->
                    navController.navigateUp()

                is MemoryDetailsSideEffect.ShowSuccessMessage ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

                is MemoryDetailsSideEffect.ShowError ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

                is MemoryDetailsSideEffect.NavigateToEdit ->
                    navController.navigate(AppRoute.MemoryFormEdit(memoryId = memoryId))
            }
        }
    }

    MemoryDetailsScreenContent(
        state = uiState,
        onEvent = viewModel::onEvent
    )

}

@Composable
fun MemoryDetailsScreenContent(
    state: MemoryDetailsUiState,
    onEvent: (MemoryDetailsScreenEvent) -> Unit
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val paddingHorizontalValue = if (isPortrait) 0 else 60

    val modifierBottomBar = if (isPortrait) {
        Modifier
            .padding(horizontal = 26.dp)
            .padding(top = 16.dp, bottom = 50.dp)
    } else {
        Modifier.padding(horizontal = 275.dp, vertical = 16.dp)
    }

    val imageBitmap = rememberImageBitmap(
        state.imageUri
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopbarComponent(
                screenName = "Detalhes da Memória",
                onBackClick = { onEvent(MemoryDetailsScreenEvent.OnBackClick)}
            )
        },
        bottomBar = {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = paddingHorizontalValue.dp)
                    .fillMaxWidth()
            )

            Row(
                modifier = modifierBottomBar,
                horizontalArrangement = Arrangement.Center
            ) {
                ExtendedFAB(
                    icon = Icons.Filled.BorderColor,
                    text = "Editar",
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onEvent(MemoryDetailsScreenEvent.OnEditClick(memoryId = state.id)) }
                )

                Spacer(Modifier.size(16.dp))

                ExtendedFAB(
                    icon = Icons.Filled.Delete,
                    text = "Excluir",
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    onClick = { onEvent(MemoryDetailsScreenEvent.OnDeleteClick(memoryId = state.id)) }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .padding(horizontal = paddingHorizontalValue.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 16.dp)
            ) {

                item {
                    MemoryDetailsImageComponent(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        imageBitmap = imageBitmap
                    )
                }

                item { Spacer(modifier = Modifier.size(26.dp)) }

                item {
                    Text(
                        text = state.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 24.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }

                item { Spacer(modifier = Modifier.size(12.dp)) }

                item {
                    Text(
                        text = state.date,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                    )
                }

                item { Spacer(modifier = Modifier.size(12.dp)) }

                item {
                    Text(
                        text = state.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 16.sp
                        ),
                    )
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun MemoryDetailsScreenView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            MemoryDetailsScreenContent(
                state = MemoryDetailsUiState()
            ) {}
        }
    }
}