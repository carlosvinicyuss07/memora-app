package com.example.memoraapp.presentation.ui.components.imagelayouts

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun DashedBorderImage(
    modifier: Modifier = Modifier,
    imageBitmap: ImageBitmap?,
    cornerRadius: Dp = 10.dp,
    strokeWidth: Dp = 2.dp,
    dashLength: Float = 12f,
    gapLength: Float = 12f
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(175.dp),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        left = 0f,
                        top = 0f,
                        right = size.width,
                        bottom = size.height,
                        radiusX = cornerRadius.toPx(),
                        radiusY = cornerRadius.toPx()
                    )
                )
            }

            drawPath(
                path = path,
                color = Color.LightGray,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(dashLength, gapLength)
                    )
                )
            )
        }

        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap,
                contentDescription = "Preview Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(6.dp)
                    .clip(RoundedCornerShape(cornerRadius)),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(text = stringResource(R.string.nenhuma_imagem_selecionada), modifier = Modifier.padding(8.dp))
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
private fun DashedBorderImageView() {
    MemoraAppTheme {
        Surface {
            DashedBorderImage(
                imageBitmap = null
            )
        }
    }
}