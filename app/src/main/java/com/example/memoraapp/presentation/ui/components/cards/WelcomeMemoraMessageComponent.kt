package com.example.memoraapp.presentation.ui.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun WelcomeMemoraMessageComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(464.dp)
            .width(390.dp)
            .padding(20.dp)
            .border(
                width = 0.4.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = stringResource(R.string.welcome_title),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(top = 16.dp)
        )

        Spacer(modifier = modifier.size(10.dp))

        Image(
            painter = painterResource(R.drawable.logo_memora_app_oficial),
            contentDescription = "Logo MemoraApp",
            modifier = Modifier
                .height(224.dp)
                .width(224.dp)
        )

        Spacer(modifier = modifier.size(10.dp))

        Text(
            text = stringResource(R.string.welcome_subtitulo),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier
                .width(270.dp)
                .height(56.dp)
        )

        Text(
            text = stringResource(R.string.welcome_text),
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
private fun WelcomeMemoraMessageComponentView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            WelcomeMemoraMessageComponent()
        }
    }
}