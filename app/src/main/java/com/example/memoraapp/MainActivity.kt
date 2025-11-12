package com.example.memoraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.memoraapp.ui.components.ToolbarWithNoReturnComponent
import com.example.memoraapp.ui.components.WelcomeContainer
import com.example.memoraapp.ui.theme.MemoraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoraAppTheme {
                WelcomeContainer()
            }
        }
    }
}
