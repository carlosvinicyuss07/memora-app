package com.example.memoraapp.ui.screens.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.memoraapp.R
import com.example.memoraapp.domain.viewmodels.CameraViewModel
import com.example.memoraapp.domain.viewmodels.ImagePickerViewModel
import com.example.memoraapp.ui.components.buttons.CaptureButton
import com.example.memoraapp.ui.components.buttons.CircleShapeSmallFAB
import com.example.memoraapp.ui.components.imagelayouts.CameraPreview
import com.example.memoraapp.ui.theme.MemoraAppTheme
import com.example.memoraapp.ui.util.capturePhoto
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SourceLockedOrientationActivity", "ContextCastToActivity")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    navController: NavController,
    imagePickerViewModel: ImagePickerViewModel,
    viewModel: CameraViewModel = koinViewModel()
) {

    val activity = LocalContext.current as Activity

    val context = LocalContext.current

    val currentLensFacing = viewModel.lensFacing

    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    val cameraPermission =
        rememberPermissionState(Manifest.permission.CAMERA)

    DisposableEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    LaunchedEffect(Unit) {
        if (!cameraPermission.status.isGranted) {
            cameraPermission.launchPermissionRequest()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is CameraSideEffect.ReturnPhoto -> {

                    imagePickerViewModel.setSelectedImage(effect.uri.toUri())

                    // Remove Camera
                    navController.popBackStack()

                    // Remove PhotoSelection
                    navController.popBackStack()
                }

                is CameraSideEffect.ShowError ->
                    Toast.makeText(context, effect.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    when {
        cameraPermission.status.isGranted -> {
            CameraScreenContent(
                onCapture = {
                    imageCapture?.let {
                        capturePhoto(
                            imageCapture = it,
                            context = context
                        ) { uri ->
                            viewModel.onEvent(
                                CameraEvent.OnPhotoCaptured(uri = uri.toString())
                            )
                        }
                    }
                },
                onSwitchCamera = {
                    viewModel.onEvent(CameraEvent.OnSwitchCamera)
                },
                preview = {
                    CameraPreview(
                        lensFacing = currentLensFacing
                    ) {
                        imageCapture = it
                    }
                }
            )
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.permissao_de_camera_necessaria))
            }
        }
    }
}

@Composable
fun CameraScreenContent(
    onCapture: () -> Unit,
    onSwitchCamera: () -> Unit,
    preview: @Composable () -> Unit
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val paddingTopValue = if (isPortrait) 108 else 0

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingTopValue.dp, bottom = 16.dp)
            )

            Box(
                modifier = Modifier
                    .width(342.dp)
                    .height(584.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                preview()
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(Modifier.size(48.dp))

                CaptureButton(onClick = onCapture)

                CircleShapeSmallFAB(
                    onClick = onSwitchCamera,
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    icon = Icons.Default.Cached
                )
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
fun CameraScreenView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CameraScreenContent(
                onCapture = {},
                onSwitchCamera = {},
                preview = {}
            )
        }
    }
}