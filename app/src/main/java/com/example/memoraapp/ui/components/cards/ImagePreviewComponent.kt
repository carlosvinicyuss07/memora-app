package com.example.memoraapp.ui.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.R
import com.example.memoraapp.ui.components.buttons.FilledButtonComponent
import com.example.memoraapp.ui.components.imagelayouts.DashedBorderImage
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun ImagePreviewComponent(
    modifier: Modifier = Modifier,
    imageRes: Int?,
    onSelectImage: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "Pré-visualização da Imagem",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Component de imagem com borda pontilhada
        DashedBorderImage(imageRes = imageRes)

        Spacer(modifier = Modifier.height(15.dp))

        FilledButtonComponent(
            text = "Selecionar Foto",
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(name = "Prévia Imagem Light")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Prévia Imagem Dark"
)
@Composable
private fun ImagePreviewComponentPreview() {
    MemoraAppTheme {
        Surface {
            ImagePreviewComponent(
                imageRes = R.drawable.photo_example_memorycard,
                onSelectImage = {}
            )
        }
    }
}
