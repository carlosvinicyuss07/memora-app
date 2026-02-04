package com.example.memoraapp.presentation.ui.components.topbar

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun TopbarComponent(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    screenName: String,
    onBackClick: () -> Unit = {}
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Row(
        modifier = modifier
            .height(108.dp)
            .fillMaxWidth()
            .padding(bottom = 28.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {

        val modifierIcon = if (isPortrait) {
            modifier
                .width(48.dp)
                .height(24.dp)
                .padding(start = 24.dp)
        } else {
            modifier
                .width(108.dp)
                .height(24.dp)
                .padding(start = 60.dp)
        }

        Icon(
            imageVector = icon ?: Icons.Filled.ArrowBackIosNew,
            contentDescription = "IconToolbar",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = if (icon == null) modifierIcon.clickable { onBackClick() } else modifierIcon
        )

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
private fun TopbarComponentView() {
    MemoraAppTheme {
        Surface {
            TopbarComponent(icon = Icons.Filled.Home, screenName = "Bem-vindo")
        }
    }
}