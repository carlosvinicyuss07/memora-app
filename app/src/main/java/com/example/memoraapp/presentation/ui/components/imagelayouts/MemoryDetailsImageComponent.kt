package com.example.memoraapp.presentation.ui.components.imagelayouts

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun MemoryDetailsImageComponent(
    modifier: Modifier = Modifier,
    imageUrl: String?
) {
    Box(
        modifier = modifier
            .width(342.dp)
            .height(220.dp),
        contentAlignment = Alignment.Center
    ) {

        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Preview Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.None
            )
        } else {
            Text(
                text = stringResource(R.string.nenhuma_imagem_selecionada),
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@Preview(name = "Prévia Imagem Light")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Prévia Imagem Dark"
)
@Composable
private fun MemoryDetailsImageComponentView() {
    MemoraAppTheme {
        Surface {
            MemoryDetailsImageComponent(imageUrl = null)
        }
    }
}