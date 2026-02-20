package com.example.memoraapp.presentation.ui.screens.auth.login

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
fun LoginScreenContent(
    state: LoginUiState,
    onEvent: (LoginScreenEvent) -> Unit
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Column(
        modifier = Modifier
            .padding(top = if (isPortrait) 30.dp else 20.dp)
            .padding(horizontal = 24.dp)
            .navigationBarsPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.auth_memora_image_sf),
            contentDescription = "Login Image",
            modifier = Modifier
                .height(144.dp)
                .width(144.dp)
        )

        Text(
            text = stringResource(R.string.memora),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 30.sp, fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = stringResource(R.string.bem_vindo_de_volta),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.outlineVariant
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 42.dp)
        )

        AuthTextFieldComponent(
            label = stringResource(R.string.endereco_de_email),
            value = state.email,
            onValueChange = { onEvent(LoginScreenEvent.OnEmailChange(it)) },
            placeholder = "name@example.com",
            leadingIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        AuthTextFieldComponent(
            label = stringResource(R.string.senha),
            value = state.password,
            onValueChange = { onEvent(LoginScreenEvent.OnPasswordChange(it)) },
            placeholder = "Digite sua senha",
            leadingIcon = Icons.Default.Lock,
            isPassword = true,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        FilledButtonComponent(
            text = stringResource(R.string.login),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
            onClick = { onEvent(LoginScreenEvent.OnLoginClick) }
        )

        TextBetweenDividersComponent(
            modifier = Modifier.padding(vertical = 32.dp),
            text = stringResource(R.string.ou_continue_com)
        )

        RedirectButtonWithIconComponent(
            text = stringResource(R.string.continue_com_google),
            icon = ImageVector.vectorResource(R.drawable.ic_google),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            isIconTintUnspecified = true,
            onClick = { onEvent(LoginScreenEvent.OnContinueWithGoogleClick) },
            modifier = Modifier.padding(bottom = 125.dp)
        )

        AuthRedirectText(
            prefixText = stringResource(R.string.nao_tem_uma_conta),
            actionText = stringResource(R.string.inscrever_se),
            onClick = { onEvent(LoginScreenEvent.OnSignUpClick) }
        )
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
            LoginScreenContent(
                state = LoginUiState()
            ) { }
        }
    }
}