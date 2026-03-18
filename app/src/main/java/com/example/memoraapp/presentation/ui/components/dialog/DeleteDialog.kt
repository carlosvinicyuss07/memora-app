package com.example.memoraapp.presentation.ui.components.dialog

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun DeleteDialog(
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(title)
        },
        text = {
            Text(text)
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

@Preview(name = "Delete Account Dialog Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Delete Account Dialog Dark Mode"
)
@Composable
private fun DeleteAccountDialogView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DeleteDialog(
                title = stringResource(R.string.excluir_conta),
                text = stringResource(R.string.tem_certeza_que_deseja_excluir_sua_conta)
                        + stringResource(R.string.todos_os_seus_dados_serao_apagados_permanentemente),
                onConfirm = {}
            ) {}
        }
    }
}

@Preview(name = "Delete Memory Dialog Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Delete Memory Dialog Dark Mode"
)
@Composable
private fun DeleteMemoryDialogView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            DeleteDialog(
                title = stringResource(R.string.excluir_memoria),
                text = stringResource(R.string.tem_certeza_que_deseja_excluir_essa_memoria)
                        + stringResource(R.string.depois_de_apagada_voce_nao_conseguir_recuperar_essa_memoria_novamente
                ),
                onConfirm = {}
            ) {}
        }
    }
}