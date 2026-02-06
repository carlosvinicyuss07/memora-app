package com.example.memoraapp.presentation.viewmodels

import com.example.memoraapp.presentation.ui.screens.photoselection.PhotoSelectionScreenEvent
import com.example.memoraapp.presentation.ui.screens.photoselection.PhotoSelectionSideEffect
import com.example.memoraapp.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoSelectionViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PhotoSelectionViewModel

    @Before
    fun setup() {
        viewModel = PhotoSelectionViewModel()
    }

    @Test
    fun `quando clicar no botao de voltar deve retornar para a tela anterior`() = runTest {
        viewModel.onEvent(PhotoSelectionScreenEvent.OnBack)

        val effect = viewModel.effects.first()
        assertTrue(effect is PhotoSelectionSideEffect.NavigateBack)
    }

    @Test
    fun `quando clicar no botao de tirar foto deve direcionar para a camera do app`() = runTest {
        viewModel.onEvent(PhotoSelectionScreenEvent.OnClickCamera)

        val effect = viewModel.effects.first()
        assertTrue(effect is PhotoSelectionSideEffect.NavigateToCamera)
    }

    @Test
    fun `quando clicar no botao de escolher da galeria deve direcionar para a galeria do celular`() = runTest {
        viewModel.onEvent(PhotoSelectionScreenEvent.OnClickGallery)

        val effect = viewModel.effects.first()
        assertTrue(effect is PhotoSelectionSideEffect.NavigateToGallery)
    }
}