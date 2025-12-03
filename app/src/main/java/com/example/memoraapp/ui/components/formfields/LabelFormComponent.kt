package com.example.memoraapp.ui.components.formfields

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.ui.theme.MemoraAppTheme
import kotlin.math.min

@Composable
fun LabelFormComponent(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    minLines: Int = 1
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        )

        Spacer(modifier = modifier.size(5.dp))

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            minLines = minLines,
            maxLines = Int.MAX_VALUE,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(16.dp)
                )
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
private fun LabelFormComponentView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            LabelFormComponent(
                title = "Título da Memória",
                value = "Pôr do sol na praia",
                onValueChange = {},
                placeholder = "Pôr do sol na praia"
            )
        }
    }
}