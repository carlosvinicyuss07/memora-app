package com.example.memoraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Alignment
import com.example.memoraapp.ui.components.ToolbarWithIconComponent
import com.example.memoraapp.ui.components.WelcomeContainer
import com.example.memoraapp.ui.theme.MemoraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoraAppTheme {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ToolbarWithIconComponent(icon = Icons.Filled.Home, screenName = "Bem-vindo")
                    WelcomeContainer()
                }
            }
        }
    }
}
