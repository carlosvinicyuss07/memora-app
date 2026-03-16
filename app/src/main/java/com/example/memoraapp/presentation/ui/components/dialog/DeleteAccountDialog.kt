package com.example.memoraapp.presentation.ui.components.dialog

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeScreenContent
import com.example.memoraapp.presentation.ui.screens.welcome.WelcomeUiState
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun DeleteAccountDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Excluir conta")
        },
        text = {
            Text(
                "Tem certeza que deseja excluir sua conta? " +
                "Todos os seus dados serão apagados permanentemente."
            )
        },
        confirmButton = {

            TextButton(onClick = onConfirm) {
                Text("Excluir")
            }
        },
        dismissButton = {

            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun WelcomeScreenView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DeleteAccountDialog(
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}