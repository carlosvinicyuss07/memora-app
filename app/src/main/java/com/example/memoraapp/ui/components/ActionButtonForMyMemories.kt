package com.example.memoraapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun ActionButtonForMyMemories(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    SmallFloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.scrim,
        contentColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .size(56.dp)
    ) {
        Icon(Icons.Filled.PhotoCamera, "Action Button para a tela Minhas Memórias", modifier = Modifier.size(24.dp))
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun ActionButtonForMyMemoriesView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ActionButtonForMyMemories(onClick = {})
        }
    }
}