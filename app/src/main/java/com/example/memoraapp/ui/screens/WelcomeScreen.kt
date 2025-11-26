package com.example.memoraapp.ui.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.ui.components.buttons.ActionButtonForMyMemories
import com.example.memoraapp.ui.components.topbar.ToolbarWithIconComponent
import com.example.memoraapp.ui.components.cards.WelcomeMemoraMessageComponent
import com.example.memoraapp.ui.theme.MemoraAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    MemoraAppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                ToolbarWithIconComponent(icon = Icons.Filled.Home, screenName = "Bem-vindo")
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(bottom = 133.dp)
                        .fillMaxWidth()
                )
                WelcomeMemoraMessageComponent()
                ActionButtonForMyMemories(
                    onClick = onStartClick,
                    modifier = Modifier
                        .align(Alignment.End)
                        .offset(x = (-25).dp, y = 20.dp)
                )
            }
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
private fun WelcomeScreenView() {
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        WelcomeScreen(onStartClick = {})
    }
}