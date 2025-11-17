@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memoraapp.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.ui.components.ActionButtonForMyMemories
import com.example.memoraapp.ui.components.ToolbarWithIconComponent
import com.example.memoraapp.ui.components.WelcomeMemoraMessageComponent
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun MemoraScaffold(
    title: String? = null,
    icon: ImageVector? = null,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            if (title != null) {
                ToolbarWithIconComponent(icon = icon, screenName = title)
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .height(10.dp),
                containerColor = MaterialTheme.colorScheme.background
            ) {}
        },
        floatingActionButton = {
            floatingActionButton?.invoke()
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(bottom = 133.dp))
            content(paddingValues)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun MemoraScaffoldView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            MemoraScaffold(title = "Bem-vindo", icon = Icons.Filled.Home, floatingActionButton = { ActionButtonForMyMemories() }, content = { WelcomeMemoraMessageComponent() })
        }
    }
}