package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.example.memoraapp.data.repository.FakeMemoryRepository
import com.example.memoraapp.presentation.ui.screens.form.FormMemoryScreenEvent
import com.example.memoraapp.presentation.ui.screens.form.FormMemorySideEffect
import com.example.memoraapp.presentation.ui.util.UiText
import com.example.memoraapp.util.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class FormMemoryViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakeMemoryRepository
    private lateinit var imagePickerViewModel: ImagePickerViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: FormMemoryViewModel

    @Before
    fun setup() {
        repository = FakeMemoryRepository()
        imagePickerViewModel = ImagePickerViewModel()
        savedStateHandle = SavedStateHandle()

        viewModel = FormMemoryViewModel(
            repository = repository,
            imagePickerViewModel = imagePickerViewModel,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `ao entrar na tela em modo cadastro deve configurar textos`() = runTest {
        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnInit(null)) // id == null para cadastrar nova memória

        // THEN
        val state = viewModel.uiState.value

        assertTrue(state.screenName is UiText.StringResource)
        assertTrue(state.buttonText is UiText.StringResource)
    }

    @Test
    fun `quando salvar com titulo vazio deve emitir erro`() = runTest {
        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // THEN
        val effect = viewModel.effects.first()

        assertTrue(effect is FormMemorySideEffect.ShowError)
    }

    @Test
    fun `quando salvar com data vazia deve emitir erro`() = runTest {
        // GIVEN
        viewModel.onEvent(FormMemoryScreenEvent.OnTitleChange("Teste"))
        viewModel.onEvent(FormMemoryScreenEvent.OnImageSelected("uri_teste"))

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // THEN
        val effect = viewModel.effects.first()

        assertTrue(effect is FormMemorySideEffect.ShowError)
    }

    @Test
    fun `quando salvar sem uma imagem deve emitir erro`() = runTest {
        // GIVEN
        viewModel.onEvent(FormMemoryScreenEvent.OnTitleChange("Teste"))
        viewModel.onEvent(FormMemoryScreenEvent.OnDateChange(LocalDate.now()))

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // THEN
        val effect = viewModel.effects.first()

        assertTrue(effect is FormMemorySideEffect.ShowError)
    }

    @Test
    fun `quando titulo muda deve atualizar uiState`() = runTest {
        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnTitleChange("Minha memória"))

        // THEN
        val state = viewModel.uiState.value
        assertEquals("Minha memória", state.title)
    }

    @Test
    fun `quando descricao muda deve atualizar uiState`() = runTest {
        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnDescriptionChange("Nova descrição"))

        // THEN
        val state = viewModel.uiState.value
        assertEquals("Nova descrição", state.description)
    }

    @Test
    fun `quando data da memoria muda deve atualizar uiState`() = runTest {
        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnDateChange(LocalDate.now()))

        // THEN
        val state = viewModel.uiState.value
        assertEquals(LocalDate.now(), state.date)
    }

    @Test
    fun `quando imagem muda deve atualizar uiState`() = runTest {
        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnImageSelected("Imagem Teste"))

        // THEN
        val state = viewModel.uiState.value
        assertEquals("Imagem Teste", state.imageUri)
    }

    @Test
    fun `quando clicar em selecionar foto deve direcionar para a tela de escolha da fonte`() = runTest {
        viewModel.onEvent(FormMemoryScreenEvent.OnSelectPhotoClick)

        val effect = viewModel.effects.first()
        assertTrue(effect is FormMemorySideEffect.NavigateToPhotoSource)
    }

    @Test
    fun `quando salvar nova memoria com dados validos deve exibir mensagem de sucesso`() = runTest {
        // GIVEN
        viewModel.onEvent(FormMemoryScreenEvent.OnTitleChange("Teste"))
        viewModel.onEvent(FormMemoryScreenEvent.OnDateChange(LocalDate.now()))
        viewModel.onEvent(FormMemoryScreenEvent.OnImageSelected("uri_teste"))

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // Avança execução das coroutines
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first()
        assertTrue(effect is FormMemorySideEffect.ShowSuccessMessage)
    }

    @Test
    fun `quando salvar nova memoria com dados validos deve fechar tela`() = runTest {
        // GIVEN
        viewModel.onEvent(FormMemoryScreenEvent.OnTitleChange("Teste"))
        viewModel.onEvent(FormMemoryScreenEvent.OnDateChange(LocalDate.now()))
        viewModel.onEvent(FormMemoryScreenEvent.OnImageSelected("uri_teste"))

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // Avança execução das coroutines
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first { it is FormMemorySideEffect.CloseScreen }
        assertTrue(effect is FormMemorySideEffect.CloseScreen)
    }

    @Test
    fun `ao voltar deve fechar tela`() = runTest {
        imagePickerViewModel.setSelectedImage("teste")

        viewModel.onEvent(FormMemoryScreenEvent.OnBackClick)

        val effect = viewModel.effects.first()
        assertTrue(effect is FormMemorySideEffect.CloseScreen)
    }

    @Test
    fun `ao voltar deve limpar imagem selecionada`() = runTest {
        imagePickerViewModel.setSelectedImage("teste")

        viewModel.onEvent(FormMemoryScreenEvent.OnBackClick)

        assertNull(imagePickerViewModel.selectedImageUri.value)
    }
}