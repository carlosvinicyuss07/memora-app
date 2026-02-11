package com.example.memoraapp.presentation.ui.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.components.buttons.FilledButtonComponent
import com.example.memoraapp.presentation.ui.components.imagelayouts.DashedBorderImage
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun ImagePreviewComponent(
    imageBitmap: ImageBitmap?,
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
            text = stringResource(R.string.pre_visualizacao_da_imagem),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Component de imagem com borda pontilhada
        DashedBorderImage(imageBitmap = imageBitmap)

        Spacer(modifier = Modifier.height(15.dp))

        FilledButtonComponent(
            text = stringResource(R.string.selecionar_foto),
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onBackground,
            onClick = onSelectImage
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
                imageBitmap = null
            ) {}
        }
    }
}
