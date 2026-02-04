package com.example.memoraapp.presentation.ui.screens.welcome

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.components.buttons.ActionButtonForMyMemories
import com.example.memoraapp.presentation.ui.components.cards.WelcomeMemoraMessageComponent
import com.example.memoraapp.presentation.ui.components.topbar.TopbarComponent
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopbarComponent(
                icon = Icons.Filled.Home,
                screenName = stringResource(R.string.bem_vindo)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        if (isPortrait) {
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
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 50.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Row {
                    WelcomeMemoraMessageComponent()

                    Spacer(modifier = Modifier.size(160.dp))

                    ActionButtonForMyMemories(
                        onClick = onStartClick,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }
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
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            WelcomeScreen(onStartClick = {})
        }
    }
}