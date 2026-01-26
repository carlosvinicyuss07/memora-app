package com.example.memoraapp.ui.screens.photoselection

import android.content.Intent
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.memoraapp.R
import com.example.memoraapp.domain.viewmodels.ImagePickerViewModel
import com.example.memoraapp.domain.viewmodels.PhotoSelectionViewModel
import com.example.memoraapp.ui.AppRoute
import com.example.memoraapp.ui.components.buttons.SourceImageOptionsComponent
import com.example.memoraapp.ui.theme.MemoraAppTheme
import com.example.memoraapp.ui.util.copyUriToCache
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

            imagePickerViewModel.setSelectedImage(localUri.toUri())

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
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

        SourceImageOptionsComponent(
            text = stringResource(R.string.tirar_foto),
            icon = Icons.Default.PhotoCamera,
            onClick = { onEvent(PhotoSelectionScreenEvent.OnClickCamera) }
        )

        Spacer(modifier = Modifier.size(16.dp))

        SourceImageOptionsComponent(
            text = stringResource(R.string.escolher_da_galeria),
            icon = Icons.Default.Image,
            onClick = { onEvent(PhotoSelectionScreenEvent.OnClickGallery) }
        )
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