package com.example.memoraapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun ToolbarWithNoReturnComponent(modifier: Modifier = Modifier, icon: ImageVector, screenName: String) {
    Row(
        modifier = modifier
            .height(68.dp)
            .width(390.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Icon(
            imageVector = icon,
            contentDescription = "IconToolbarWithNoReturn",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.size(24.dp)
        )

        Spacer(modifier = modifier.size(8.dp))

        Text(
            text = screenName,
            fontFamily = FontFamily.Monospace,
            color = if (isSystemInDarkTheme()) Color.White else Color.Black
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun ToolbarWithNoReturnComponentView() {
    MemoraAppTheme {
        Surface {
            ToolbarWithNoReturnComponent(icon = Icons.Filled.Home, screenName = "Bem-vindo")
        }
    }
}