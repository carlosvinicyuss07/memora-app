@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memoraapp.ui.screens.form

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memoraapp.domain.viewmodels.FormMemoryViewModel
import com.example.memoraapp.ui.AppRoute
import com.example.memoraapp.ui.components.buttons.FilledButtonComponent
import com.example.memoraapp.ui.components.cards.ImagePreviewComponent
import com.example.memoraapp.ui.components.formfields.LabelDateFormComponent
import com.example.memoraapp.ui.components.formfields.LabelFormComponent
import com.example.memoraapp.ui.components.topbar.TopbarComponent
import com.example.memoraapp.ui.theme.MemoraAppTheme
import com.example.memoraapp.ui.util.rememberImageBitmap
import org.koin.androidx.compose.koinViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import com.example.memoraapp.ui.util.uriToImageBitmap

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedGetBackStackEntry")
@Composable
fun FormMemoryScreen(
    navController: NavController,
    viewModel: FormMemoryViewModel = koinViewModel(),
    memoryId: Int?
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val graphEntry = remember {
        navController.getBackStackEntry(AppRoute.MemoryFormGraph)
    }

    val photoUri by graphEntry
        .savedStateHandle
        .getStateFlow<String?>("photo_uri", null)
        .collectAsState()

    LaunchedEffect(photoUri) {
        photoUri?.let {
            viewModel.onEvent(
                FormMemoryScreenEvent.OnImageSelected(it)
            )
        }
    }


    LaunchedEffect(Unit) {
        viewModel.onEvent(FormMemoryScreenEvent.OnInit(memoryId))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                FormMemorySideEffect.CloseScreen ->
                    navController.navigateUp()

                is FormMemorySideEffect.ShowSuccessMessage ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

                is FormMemorySideEffect.ShowError ->
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()

                is FormMemorySideEffect.NavigateToPhotoSource ->
                    navController.navigate(AppRoute.PhotoSource)
            }
        }
    }

    FormMemoryScreenContent(
        state = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun FormMemoryScreenContent(
    state: FormMemoryUiState,
    onEvent: (FormMemoryScreenEvent) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = state.date?.toEpochDay()?.let {
            it * 24L * 60L * 1000L
        }
    )

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis ?: return@TextButton
                    val selectedDate = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()

                    onEvent(FormMemoryScreenEvent.OnDateChange(selectedDate))
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val imageBitmap = rememberImageBitmap(
        state.imageUri
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopbarComponent(
                screenName = state.screenName,
                onBackClick = { onEvent(FormMemoryScreenEvent.OnBackClick)}
            )
        }
    ) { paddingValues ->

        val paddingHorizontalValue = if (isPortrait) 0 else 60

        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
                .padding(horizontal = paddingHorizontalValue.dp)
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
                        value = state.title,
                        onValueChange = { onEvent(FormMemoryScreenEvent.OnTitleChange(it)) },
                        placeholder = "Dê um titúlo marcante a sua memória"
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    LabelFormComponent(
                        title = "Descrição",
                        value = state.description,
                        onValueChange = { onEvent(FormMemoryScreenEvent.OnDescriptionChange(it)) },
                        placeholder = "Descreva sua memória",
                        minLines = 4
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    LabelDateFormComponent(
                        title = "Data da Memória",
                        value = state.date?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        placeholder = "Selecione uma data",
                        onClick = { showDatePicker = true }
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    ImagePreviewComponent(
                        imageBitmap = imageBitmap,
                        onSelectImage = { onEvent(FormMemoryScreenEvent.OnSelectPhotoClick) }
                    )
                }

                item { Spacer(modifier = Modifier.size(6.dp)) }

                item {
                    FilledButtonComponent(
                        text = state.buttonText,
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        onClick = { onEvent(FormMemoryScreenEvent.OnSave) }
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
            FormMemoryScreenContent(
                state = FormMemoryUiState()
            ) { }
        }
    }
}