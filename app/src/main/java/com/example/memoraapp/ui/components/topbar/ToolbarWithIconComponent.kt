package com.example.memoraapp.ui.components.topbar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun ToolbarWithIconComponent(modifier: Modifier = Modifier, icon: ImageVector?, screenName: String) {
    Row(
        modifier = modifier
            .height(68.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "IconToolbar",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = modifier
                    .width(48.dp)
                    .height(24.dp)
                    .padding(start = 24.dp)
            )
        }

        Spacer(modifier = modifier.size(8.dp))

        Text(
            text = screenName,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
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
private fun ToolbarWithIconComponentView() {
    MemoraAppTheme {
        Surface {
            ToolbarWithIconComponent(icon = Icons.Filled.Home, screenName = "Bem-vindo")
        }
    }
}