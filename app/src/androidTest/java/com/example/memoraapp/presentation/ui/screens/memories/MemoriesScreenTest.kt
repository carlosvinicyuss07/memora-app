package com.example.memoraapp.presentation.ui.screens.memories

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MemoriesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun deveExibirTituloNaTela() {

        composeTestRule.setContent {
            MemoriesScreenContent(
                state = MemoriesScreenState(),
                onEvent = {}
            )
        }

        composeTestRule
            .onNodeWithText("Minhas Memórias")
            .assertIsDisplayed()
    }

    @Test
    fun deveExibirMemoriasQuandoEstadoTiverItens() {
        val state = MemoriesScreenState(
            memories = listOf(
                MemoryUi(1, "Memória 1", "Desc", "01-01-2026"),
                MemoryUi(2, "Memória 2", "Desc", "01-01-2026")
            )
        )

        composeTestRule.setContent {
            MemoriesScreenContent(state = state, onEvent = {})
        }

        composeTestRule
            .onAllNodesWithTag("memory_card")
            .assertCountEquals(2)
    }

    @Test
    fun aoRolarScrollAteOFinalDeveExibirTodasAsMemorias() {
        val state = MemoriesScreenState(
            memories = (1..20).map {
                MemoryUi(it, "Memória $it", "Desc", "01-01-2026")
            }
        )

        composeTestRule.setContent {
            MemoriesScreenContent(state = state, onEvent = {})
        }

        composeTestRule
            .onNodeWithTag("memories_grid")
            .performScrollToIndex(19) // index começa em 0

        composeTestRule
            .onNodeWithText("Memória 20")
            .assertIsDisplayed()
    }

    @Test
    fun aoClicarNoFabDeveAcionarEventoAdicionado() {
        var receivedEvent: MemoriesScreenEvent? = null

        composeTestRule.setContent {
            MemoriesScreenContent(
                state = MemoriesScreenState(),
                onEvent = { receivedEvent = it }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("FAB Nova Memória")
            .performClick()

        assertEquals(MemoriesScreenEvent.OnAddMemoryClick, receivedEvent)
    }

    @Test
    fun aoClicarNoCardDeMemoriaOEventoComIdCorretoDeveSerAcionado() {
        var receivedEvent: MemoriesScreenEvent? = null

        val state = MemoriesScreenState(
            memories = listOf(
                MemoryUi(10, "Memória X", "Desc", "01-01-2026")
            )
        )

        composeTestRule.setContent {
            MemoriesScreenContent(
                state = state,
                onEvent = { receivedEvent = it }
            )
        }

        composeTestRule
            .onNodeWithText("Memória X")
            .performClick()

        assertEquals(
            MemoriesScreenEvent.OnMemoryClick(10),
            receivedEvent
        )
    }

    @Test
    fun aoClicarNoBotaoDeVoltarDeveAcionarEventoDeRetorno() {
        var receivedEvent: MemoriesScreenEvent? = null

        composeTestRule.setContent {
            MemoriesScreenContent(
                state = MemoriesScreenState(),
                onEvent = { receivedEvent = it }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("IconToolbar")
            .performClick()

        assertEquals(MemoriesScreenEvent.OnBackClick, receivedEvent)
    }

    @Test
    fun quandoNaoHouverMemoriasDeveMostrarListaVazia() {
        composeTestRule.setContent {
            MemoriesScreenContent(
                state = MemoriesScreenState(memories = emptyList()),
                onEvent = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onAllNodesWithTag("memory_card")
            .assertCountEquals(0)
    }
}