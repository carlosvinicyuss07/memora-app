package com.example.memoraapp.presentation.ui.screens.auth.signup

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.components.buttons.AuthRedirectText
import com.example.memoraapp.presentation.ui.components.buttons.FilledButtonComponent
import com.example.memoraapp.presentation.ui.components.buttons.RedirectButtonWithIconComponent
import com.example.memoraapp.presentation.ui.components.dividers.TextBetweenDividersComponent
import com.example.memoraapp.presentation.ui.components.formfields.AuthTextFieldComponent
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun SignUpScreenContent(
    state: SignUpUiState,
    onEvent: (SignUpScreenEvent) -> Unit
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    LazyColumn(
        modifier = Modifier
            .padding(top = if (isPortrait) 30.dp else 20.dp)
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Image(
                painter = painterResource(R.drawable.auth_memora_image_sf),
                contentDescription = "Login Image",
                modifier = Modifier
                    .height(144.dp)
                    .width(144.dp)
            )
        }

        item {
            Text(
                text = stringResource(R.string.crie_sua_conta),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        item {
            Text(
                text = stringResource(R.string.comece_a_guardar_suas_memorias),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.outlineVariant
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        item {
            AuthTextFieldComponent(
                label = stringResource(R.string.nome_completo),
                value = state.fullName,
                onValueChange = { onEvent(SignUpScreenEvent.OnFullNameChange(it)) },
                placeholder = "Digite seu nome completo",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            AuthTextFieldComponent(
                label = stringResource(R.string.endereco_de_email),
                value = state.email,
                onValueChange = { onEvent(SignUpScreenEvent.OnEmailChange(it)) },
                placeholder = "name@example.com",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            AuthTextFieldComponent(
                label = stringResource(R.string.senha),
                value = state.password,
                onValueChange = { onEvent(SignUpScreenEvent.OnPasswordChange(it)) },
                placeholder = "Crie uma senha",
                leadingIcon = Icons.Default.Lock,
                isPassword = true,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            AuthTextFieldComponent(
                label = stringResource(R.string.confirme_sua_senha),
                value = state.confirmPassword,
                onValueChange = { onEvent(SignUpScreenEvent.OnConfirmPasswordChange(it)) },
                placeholder = "Confirme sua senha",
                leadingIcon = Icons.Default.Security,
                isPassword = true,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        item {
            FilledButtonComponent(
                text = stringResource(R.string.criar_uma_conta),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.background,
                onClick = { onEvent(SignUpScreenEvent.OnCreateAccountClick) }
            )
        }

        item {
            TextBetweenDividersComponent(
                modifier = Modifier.padding(vertical = 24.dp),
                text = stringResource(R.string.ou_cadastre_se_com)
            )
        }

        item {
            RedirectButtonWithIconComponent(
                text = stringResource(R.string.continue_com_google),
                icon = ImageVector.vectorResource(R.drawable.ic_google),
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                isIconTintUnspecified = true,
                onClick = { onEvent(SignUpScreenEvent.OnContinueWithGoogleClick) },
                modifier = Modifier.padding(bottom = 40.dp)
            )
        }

        item {
            AuthRedirectText(
                prefixText = stringResource(R.string.ja_tem_uma_conta),
                actionText = stringResource(R.string.login),
                onClick = { onEvent(SignUpScreenEvent.OnLoginClick) },
                modifier = Modifier.padding(bottom = 30.dp)
            )
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun LoginScreenView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SignUpScreenContent(
                state = SignUpUiState()
            ) { }
        }
    }
}