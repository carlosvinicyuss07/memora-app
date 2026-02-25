package com.example.memoraapp.presentation.ui.components.formfields

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun AuthTextFieldComponent(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier) {

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp, fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.outline
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                leadingIcon?.let {
                    Icon(imageVector = it, contentDescription = null)
                }
            },
            trailingIcon = {
                when {
                    isPassword -> {
                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }

                    trailingIcon != null -> {
                        IconButton(onClick = {
                            onTrailingIconClick?.invoke()
                        }) {
                            Icon(trailingIcon, contentDescription = null)
                        }
                    }
                }
            },
            visualTransformation = if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedContainerColor = MaterialTheme.colorScheme.background
            )
        )
    }
}

@Preview(name = "Nome Completo Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Nome Completo Dark Mode"
)
@Composable
private fun AuthTextFieldFullnameView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AuthTextFieldComponent(
                label = stringResource(R.string.nome_completo),
                value = "",
                onValueChange = {},
                placeholder = "Carlos Vinícyus",
                leadingIcon = Icons.Default.Person
            )
        }
    }
}

@Preview(name = "Email Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Email Dark Mode"
)
@Composable
private fun AuthTextFieldEmailView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AuthTextFieldComponent(
                label = stringResource(R.string.endereco_de_email),
                value = "",
                onValueChange = {},
                placeholder = "name@example.com",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email
            )
        }
    }
}

@Preview(name = "Senha Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Senha Dark Mode"
)
@Composable
private fun AuthTextFieldPasswordView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AuthTextFieldComponent(
                label = stringResource(R.string.senha),
                value = "12345678",
                onValueChange = {},
                placeholder = "Senha",
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )
        }
    }
}

@Preview(name = "Confirmar Senha Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Confirmar Senha Dark Mode"
)
@Composable
private fun AuthTextFieldConfirmPasswordView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AuthTextFieldComponent(
                label = stringResource(R.string.confirme_sua_senha),
                value = "12345678",
                onValueChange = {},
                placeholder = "Confirme sua senha",
                leadingIcon = Icons.Default.Security,
                isPassword = true
            )
        }
    }
}