package com.example.memoraapp.presentation.ui.screens.photoselection

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.memoraapp.R
import com.example.memoraapp.presentation.viewmodels.ImagePickerViewModel
import com.example.memoraapp.presentation.viewmodels.PhotoSelectionViewModel
import com.example.memoraapp.presentation.ui.AppRoute
import com.example.memoraapp.presentation.ui.components.buttons.RedirectButtonWithIconComponent
import com.example.memoraapp.presentation.ui.components.topbar.TopbarComponent
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme
import com.example.memoraapp.presentation.ui.util.copyUriToCache
import org.koin.androidx.compose.koinViewModel

@Composable
fun PhotoSelectionScreen(
    navController: NavController,
    imagePickerViewModel: ImagePickerViewModel,
    viewModel: PhotoSelectionViewModel = koinViewModel()
) {

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {

            val localUri = copyUriToCache(context, uri)

            imagePickerViewModel.setSelectedImage(localUri)

            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is PhotoSelectionSideEffect.NavigateToCamera ->
                    navController.navigate(AppRoute.Camera)

                is PhotoSelectionSideEffect.NavigateToGallery ->
                    galleryLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )

                is PhotoSelectionSideEffect.NavigateBack ->
                    navController.navigateUp()
            }
        }
    }

    PhotoSelectionScreenContent(
        onEvent = viewModel::onEvent
    )

}

@Composable
fun PhotoSelectionScreenContent(
    onEvent: (PhotoSelectionScreenEvent) -> Unit
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopbarComponent(
                screenName = "",
                onBackClick = { onEvent(PhotoSelectionScreenEvent.OnBack) }
            )
        }
    ) { paddingValues ->

        val paddingHorizontalValue = if (isPortrait) 0 else 60

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = paddingHorizontalValue.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = if (isPortrait) Arrangement.Center else Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.escolha_a_fonte_da_imagem),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                ),
            )

            Spacer(modifier = Modifier.size(54.dp))

            RedirectButtonWithIconComponent(
                text = stringResource(R.string.tirar_foto),
                icon = Icons.Default.PhotoCamera,
                onClick = { onEvent(PhotoSelectionScreenEvent.OnClickCamera) }
            )

            Spacer(modifier = Modifier.size(16.dp))

            RedirectButtonWithIconComponent(
                text = stringResource(R.string.escolher_da_galeria),
                icon = Icons.Default.Image,
                onClick = { onEvent(PhotoSelectionScreenEvent.OnClickGallery) }
            )
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
private fun PhotoSelectionScreenView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PhotoSelectionScreenContent {}
        }
    }
}