package com.example.memoraapp.presentation.ui.components.dividers

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun TextBetweenDividersComponent(
    modifier: Modifier = Modifier,
    text: String
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.outline
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Preview(name = "Divider Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Divider Dark Mode"
)
@Composable
private fun DividerWithTextComponentPreview() {
    MemoraAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TextBetweenDividersComponent(
                text = stringResource(R.string.ou_continue_com)
            )
        }
    }
}

@Preview(name = "Divider 2 Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Divider 2 Dark Mode"
)
@Composable
private fun DividerWithTextComponentPreview2() {
    MemoraAppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            TextBetweenDividersComponent(
                text = stringResource(R.string.ou_cadastre_se_com)
            )
        }
    }
}