package com.example.memoraapp.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun ExtendedFAB(modifier: Modifier = Modifier, icon: ImageVector, text: String, containerColor: Color, contentColor: Color) {
    ExtendedFloatingActionButton(
        onClick = {},
        icon = { Icon(icon, "Extended FAB Padrão", modifier.size(20.dp)) },
        text = { Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.SemiBold) },
        containerColor = containerColor,
        contentColor = contentColor,
        modifier = modifier
            .size(width = 171.dp, height = 48.dp)
    )
}

@Preview(name = "Edit Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Edit Dark Mode"
)
@Composable
private fun CircleShapeExtendedFABEditView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ExtendedFAB(icon = Icons.Filled.BorderColor, text = "Editar", containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Preview(name = "Delete Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Delete Dark Mode"
)
@Composable
private fun CircleShapeExtendedFABDeleteView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ExtendedFAB(icon = Icons.Filled.Delete, text = "Excluir", containerColor = MaterialTheme.colorScheme.error, contentColor = MaterialTheme.colorScheme.onError)
        }
    }
}