package com.example.memoraapp.ui.screens.form

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.memoraapp.data.FakeMemoryRepository
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.domain.viewmodels.FormMemoryUiEvent
import com.example.memoraapp.domain.viewmodels.FormMemoryViewModel
import com.example.memoraapp.ui.components.buttons.FilledButtonComponent
import com.example.memoraapp.ui.components.cards.ImagePreviewComponent
import com.example.memoraapp.ui.components.formfields.LabelDateFormComponent
import com.example.memoraapp.ui.components.formfields.LabelFormComponent
import com.example.memoraapp.ui.components.topbar.TopbarComponent
import com.example.memoraapp.ui.theme.MemoraAppTheme
import com.example.memoraapp.ui.util.showDatePickerDialog
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FormMemoryScreen(
    viewModel: FormMemoryViewModel = viewModel(),
    onSaved: () -> Unit = {}
) {

    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val pickLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        viewModel.onAction(FormMemoryAction.OnImageSelected(uri?.toString()))
    }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is FormMemoryUiEvent.Saved -> onSaved()
                is FormMemoryUiEvent.Error -> {
                    // Implementar
                }
            }
        }
    }

    val showDatePicker = remember { mutableStateOf(false) }
    if (showDatePicker.value) {
        showDatePickerDialog(context, state.date) { picked ->
            viewModel.onAction(FormMemoryAction.OnDateChange(picked))
            showDatePicker.value = false
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopbarComponent(screenName = "Nova Memória")
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

            LazyColumn(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                item {
                    LabelFormComponent(
                        title = "Título da Memória",
                        value = "Pôr do sol na praia",
                        onValueChange = {},
                        placeholder = "Dê um titúlo marcante a sua memória"
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    LabelFormComponent(
                        title = "Descrição",
                        value = "Um dia perfeito na praia, com o sol se pondo em tons vibrantes de laranja e rosa sobre o oceano calmo. Um momento de pura paz.",
                        onValueChange = {},
                        placeholder = "Descreva sua memória",
                        minLines = 4
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    LabelDateFormComponent(
                        title = "Data da Memória",
                        placeholder = "Selecione uma data",
                        onClick = { showDatePicker.value = true }
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    ImagePreviewComponent(
                        imageBitmap = null,
                        onSelectImage = {}
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    FilledButtonComponent(
                        text = "Salvar",
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background
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
private fun FormMemoryScreenView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val fakeHandle = SavedStateHandle()
            val vm = FormMemoryViewModel(savedStateHandle = fakeHandle)
            FormMemoryScreen(viewModel = vm)
        }
    }
}