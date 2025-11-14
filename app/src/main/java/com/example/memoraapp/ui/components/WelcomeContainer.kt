package com.example.memoraapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.R
import com.example.memoraapp.ui.theme.MemoraAppTheme

@Composable
fun WelcomeContainer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(434.dp)
            .width(342.dp)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Memora",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = modifier.size(13.dp))

        Image(
            painter = painterResource(R.drawable.logo_memora_app),
            contentDescription = "Logo MemoraApp",
            modifier = Modifier
                .height(223.dp)
                .width(223.dp)
        )

        Spacer(modifier = modifier.size(8.dp))

        Text(
            text = "Registre seus momentos mais preciosos.",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
                .width(270.dp)
                .height(56.dp)
        )

        Text(
            text = "Gerencie suas memórias pessoais com facilidade.",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = modifier
                .width(270.dp)
                .height(56.dp)
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
private fun WelcomeContainerView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            WelcomeContainer()
        }
    }
}