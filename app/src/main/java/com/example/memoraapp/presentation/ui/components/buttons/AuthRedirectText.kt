package com.example.memoraapp.presentation.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun AuthRedirectText(
    prefixText: String,
    actionText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    actionColor: Color = MaterialTheme.colorScheme.inversePrimary
) {
    val annotatedString = buildAnnotatedString {

        append(prefixText)
        append(" ")

        pushLink(
            LinkAnnotation.Clickable(
                tag = "action",
                linkInteractionListener = {
                    onClick()
                }
            )
        )

        withStyle(
            style = SpanStyle(
                color = actionColor,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append(actionText)
        }

        pop()
    }

    Text(
        text = annotatedString,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.outlineVariant
        )
    )
}

@Preview(name = "AuthRedirect Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "AuthRedirect Dark Mode"
)
@Composable
private fun AuthRedirectTextView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AuthRedirectText(
                prefixText = stringResource(R.string.ja_tem_uma_conta),
                actionText = stringResource(R.string.login),
                onClick = {}
            )
        }
    }
}