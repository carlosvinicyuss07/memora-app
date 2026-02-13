package com.example.memoraapp.presentation.ui.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun ExtendedFAB(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            icon,
            contentDescription = "Extended FAB Padrão",
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(name = "Edit Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Edit Dark Mode"
)
@Composable
private fun CircleShapeExtendedFABEditView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ExtendedFAB(
                icon = Icons.Filled.BorderColor,
                text = stringResource(R.string.editar),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {})
        }
    }
}

@Preview(name = "Delete Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Delete Dark Mode"
)
@Composable
private fun CircleShapeExtendedFABDeleteView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ExtendedFAB(
                icon = Icons.Filled.Delete,
                text = stringResource(R.string.excluir),
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
                onClick = {})
        }
    }
}