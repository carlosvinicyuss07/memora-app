package com.example.memoraapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.example.memoraapp.R
import com.example.memoraapp.data.repository.FakeRepositoryException
import com.example.memoraapp.data.repository.RepositoryFailure
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.presentation.ui.screens.form.FormMemoryScreenEvent
import com.example.memoraapp.presentation.ui.screens.form.FormMemorySideEffect
import com.example.memoraapp.presentation.ui.util.UiText
import com.example.memoraapp.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class FormMemoryViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: MemoryRepository
    private lateinit var imagePickerViewModel: ImagePickerViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: FormMemoryViewModel

    @Before
    fun setup() {
        repository = mockk()
        imagePickerViewModel = ImagePickerViewModel()
        savedStateHandle = SavedStateHandle()

        viewModel = FormMemoryViewModel(
            repository = repository,
            imagePickerViewModel = imagePickerViewModel,
            savedStateHandle = savedStateHandle
        )
    }

    private fun fillValidForm(isEditMode: Boolean = false) {
        if (isEditMode) {
            viewModel.onEvent(FormMemoryScreenEvent.OnTitleChange("Editado"))
        } else {
            viewModel.onEvent(FormMemoryScreenEvent.OnTitleChange("Teste"))
        }
        viewModel.onEvent(FormMemoryScreenEvent.OnDateChange(LocalDate.now()))
        viewModel.onEvent(FormMemoryScreenEvent.OnImageSelected("uri_teste"))
    }

    private fun generateOldMemory(): Memory {
        return Memory(
            id = 1,
            title = "Antigo",
            description = "Desc",
            date = LocalDate.now(),
            imageUri = "uri_teste"
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

        val errorEffect = effect as? FormMemorySideEffect.ShowError ?: error("Esperado ShowError, mas foi ${effect::class.simpleName}")

        val message = errorEffect.message as UiText.StringResource
        assertEquals(R.string.erro_titulo_obrigatorio, message.resId)
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

        val errorEffect = effect as? FormMemorySideEffect.ShowError ?: error("Esperado ShowError, mas foi ${effect::class.simpleName}")

        val message = errorEffect.message as UiText.StringResource
        assertEquals(R.string.erro_selecione_uma_data, message.resId)
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

        val errorEffect = effect as? FormMemorySideEffect.ShowError ?: error("Esperado ShowError, mas foi ${effect::class.simpleName}")

        val message = errorEffect.message as UiText.StringResource
        assertEquals(R.string.erro_imagem_obrigatoria, message.resId)
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
        // GIVEN
        val date = LocalDate.of(2026, 1, 1)

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnDateChange(date))

        // THEN
        val state = viewModel.uiState.value
        assertEquals(date, state.date)
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
        coEvery { repository.insert(any()) } just Runs

        fillValidForm()

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // Avança execução das coroutines
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first() as FormMemorySideEffect.ShowSuccessMessage
        val message = effect.message as UiText.StringResource

        assertEquals(R.string.salvo_com_sucesso, message.resId)
    }

    @Test
    fun `quando salvar nova memoria com dados validos deve fechar tela`() = runTest {
        // GIVEN
        coEvery { repository.insert(any()) } just Runs

        fillValidForm()

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // Avança execução das coroutines
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first { it is FormMemorySideEffect.CloseScreen }
        assertTrue(effect is FormMemorySideEffect.CloseScreen)
    }

    @Test
    fun `quando salvar nova memoria e repositorio falhar deve emitir erro ao salvar`() = runTest {
        // GIVEN
        coEvery { repository.insert(any()) } throws FakeRepositoryException(
            RepositoryFailure.Insert
        )

        fillValidForm()

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first() as FormMemorySideEffect.ShowError
        val message = effect.message as UiText.StringResource

        assertEquals(R.string.erro_ao_salvar, message.resId)
    }

    @Test
    fun `ao salvar nova memoria com sucesso deve limpar imagem selecionada`() = runTest {
        coEvery { repository.insert(any()) } just Runs
        imagePickerViewModel.setSelectedImage("uri_teste")

        fillValidForm()

        viewModel.onEvent(FormMemoryScreenEvent.OnSave)
        advanceUntilIdle()

        assertNull(imagePickerViewModel.selectedImageUri.value)
    }

    @Test
    fun `quando atualizar memoria com dados validos deve exibir mensagem de sucesso`() = runTest {
        // GIVEN
        val memory = generateOldMemory()

        coEvery { repository.getMemoryById(1) } returns memory
        coEvery { repository.update(any()) } just Runs

        viewModel.onEvent(FormMemoryScreenEvent.OnInit(1)) // id ! null para cadastrar nova memória
        advanceUntilIdle()

        fillValidForm(isEditMode = true)

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // Avança execução das coroutines
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first() as FormMemorySideEffect.ShowSuccessMessage
        val message = effect.message as UiText.StringResource

        assertEquals(R.string.atualizado_com_sucesso, message.resId)
    }

    @Test
    fun `quando atualizar memoria com dados validos deve fechar tela`() = runTest {
        // GIVEN
        val memory = generateOldMemory()

        coEvery { repository.getMemoryById(1) } returns memory
        coEvery { repository.update(any()) } just Runs

        viewModel.onEvent(FormMemoryScreenEvent.OnInit(1))
        advanceUntilIdle()

        fillValidForm(isEditMode = true)

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)

        // Avança execução das coroutines
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first { it is FormMemorySideEffect.CloseScreen }
        assertTrue(effect is FormMemorySideEffect.CloseScreen)
    }

    @Test
    fun `quando atualizar memoria e repositorio falhar deve emitir erro ao atualizar`() = runTest {
        // GIVEN
        val memory = generateOldMemory()

        coEvery { repository.getMemoryById(1) } returns memory
        coEvery { repository.update(any()) } throws FakeRepositoryException(
            RepositoryFailure.Update
        )

        viewModel.onEvent(FormMemoryScreenEvent.OnInit(1))
        advanceUntilIdle()

        fillValidForm(isEditMode = true)

        // WHEN
        viewModel.onEvent(FormMemoryScreenEvent.OnSave)
        advanceUntilIdle()

        // THEN
        val effect = viewModel.effects.first() as FormMemorySideEffect.ShowError
        val message = effect.message as UiText.StringResource

        assertEquals(R.string.erro_ao_atualizar, message.resId)
    }

    @Test
    fun `ao atualizar memoria nao deve limpar imagem`() = runTest {
        coEvery { repository.update(any()) } just Runs
        imagePickerViewModel.setSelectedImage("uri_teste")

        viewModel.onEvent(FormMemoryScreenEvent.OnInit(1))
        advanceUntilIdle()

        fillValidForm(isEditMode = true)

        viewModel.onEvent(FormMemoryScreenEvent.OnSave)
        advanceUntilIdle()

        assertEquals("uri_teste", imagePickerViewModel.selectedImageUri.value)
    }

    @Test
    fun `ao voltar deve fechar tela`() = runTest {
        imagePickerViewModel.setSelectedImage("uri_teste")

        viewModel.onEvent(FormMemoryScreenEvent.OnBackClick)

        val effect = viewModel.effects.first()
        assertTrue(effect is FormMemorySideEffect.CloseScreen)
    }

    @Test
    fun `ao voltar deve limpar imagem selecionada`() = runTest {
        imagePickerViewModel.setSelectedImage("uri_teste")

        viewModel.onEvent(FormMemoryScreenEvent.OnBackClick)

        assertNull(imagePickerViewModel.selectedImageUri.value)
    }
}