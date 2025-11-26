package com.example.memoraapp.ui.components.formfields

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun LabelFormComponent(
    modifier: Modifier = Modifier,
    title: String,
    memoryText: String
) {
    var textInput by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .width(342.dp)
            .height(73.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
        )

        Spacer(modifier = modifier.size(5.dp))

        TextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text(memoryText) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            ),
            maxLines = Int.MAX_VALUE,
            modifier = modifier
                .width(342.dp)
                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(16.dp))
        )
    }
}

@Preview(name = "Titulo da Memoria Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Titulo da Memoria Dark Mode"
)
@Composable
private fun LabelFormComponentTitleMemoryView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            LabelFormComponent(title = "Título da Memória", memoryText = "Pôr do Sol Inesquecível")
        }
    }
}