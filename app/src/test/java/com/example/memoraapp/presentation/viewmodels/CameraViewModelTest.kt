package com.example.memoraapp.presentation.viewmodels

import androidx.camera.core.CameraSelector
import com.example.memoraapp.presentation.ui.screens.camera.CameraEvent
import com.example.memoraapp.presentation.ui.screens.camera.CameraSideEffect
import com.example.memoraapp.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CameraViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CameraViewModel

    @Before
    fun setup() {
        viewModel = CameraViewModel()
    }

    @Test
    fun `quando clicar no botao de captura de foto deve voltar para a tela de formulario retornando a imagem capturada`() = runTest {
        val imageUri = "uri_teste"

        viewModel.onEvent(CameraEvent.OnPhotoCaptured(imageUri))

        val effect = viewModel.effects.first()

        assertEquals(CameraSideEffect.ReturnPhoto(imageUri), effect)
    }

    @Test
    fun `quando clicar no botao de inverter camera deve trocar a alternar entre frontal e traseira`() = runTest {
        // A câmera do app inicia com a câmera traseira por padrão

        viewModel.onEvent(CameraEvent.OnSwitchCamera)

        val isFrontCamera = viewModel.lensFacing == CameraSelector.LENS_FACING_FRONT

        assertTrue(isFrontCamera)

        viewModel.onEvent(CameraEvent.OnSwitchCamera)

        val isBackCamera = viewModel.lensFacing == CameraSelector.LENS_FACING_BACK

        assertTrue(isBackCamera)
    }
}