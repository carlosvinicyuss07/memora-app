package com.example.memoraapp.ui.screens.details

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.viewmodels.FormMemoryViewModel
import com.example.memoraapp.domain.viewmodels.MemoryDetailsViewModel
import com.example.memoraapp.ui.AppRoute
import com.example.memoraapp.ui.components.buttons.ExtendedFAB
import com.example.memoraapp.ui.components.imagelayouts.MemoryDetailsImageComponent
import com.example.memoraapp.ui.components.topbar.TopbarComponent
import com.example.memoraapp.ui.screens.form.FormMemoryScreenEvent
import com.example.memoraapp.ui.theme.MemoraAppTheme
import com.example.memoraapp.ui.util.rememberImageBitmap
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

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
                    navController.navigate(AppRoute.MemoryFormEdit.createRoute(memoryId))
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
        }
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

            MemoryDetailsImageComponent(
                modifier = Modifier.padding(horizontal = 24.dp),
                imageBitmap = imageBitmap
            )

            LazyColumn(
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 16.dp)
            ) {

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

            Spacer(modifier = Modifier.weight(1f))

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ExtendedFAB(
                    icon = Icons.Filled.BorderColor,
                    text = "Editar",
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    onClick = { onEvent(MemoryDetailsScreenEvent.OnEditClick(memoryId = state.id)) }
                )

                ExtendedFAB(
                    icon = Icons.Filled.Delete,
                    text = "Excluir",
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    onClick = { onEvent(MemoryDetailsScreenEvent.OnDeleteClick(memoryId = state.id)) }
                )
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