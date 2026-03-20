package com.example.memoraapp.presentation.ui.screens.userprofile

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.memoraapp.R
import com.example.memoraapp.presentation.ui.AppRoute
import com.example.memoraapp.presentation.ui.components.buttons.FilledButtonComponent
import com.example.memoraapp.presentation.ui.components.buttons.LeftAlignedButtonComponent
import com.example.memoraapp.presentation.ui.components.dialog.DeleteDialog
import com.example.memoraapp.presentation.ui.components.formfields.UserProfileFormFieldComponent
import com.example.memoraapp.presentation.ui.components.imagelayouts.UserProfilePictureComponent
import com.example.memoraapp.presentation.ui.components.texts.LeftAlignedTitleWithDescriptionComponent
import com.example.memoraapp.presentation.ui.components.topbar.TopbarComponent
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme
import com.example.memoraapp.presentation.viewmodels.ImagePickerViewModel
import com.example.memoraapp.presentation.viewmodels.UserProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserProfileScreen(
    navController: NavController,
    imagePickerViewModel: ImagePickerViewModel,
    viewModel: UserProfileViewModel = koinViewModel(),
    userId: String
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val selectedImageUri by imagePickerViewModel.selectedImageUri.collectAsState()

    LaunchedEffect(selectedImageUri) {
        selectedImageUri?.let {
            viewModel.onEvent(
                UserProfileScreenEvent.OnPhotoSelected(it.toString())
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(UserProfileScreenEvent.OnInit(userId = userId))

        viewModel.effects.collect { effect ->
            when (effect) {
                is UserProfileSideEffect.NavigateToAuth ->
                    navController.navigate(AppRoute.AuthGraph) {
                        popUpTo(AppRoute.MainGraph) {
                            inclusive = true
                        }
                    }

                is UserProfileSideEffect.CloseScreen ->
                    navController.navigateUp()

                is UserProfileSideEffect.NavigateToPhotoSource ->
                    navController.navigate(AppRoute.PhotoSource)

                is UserProfileSideEffect.ShowSuccessMessage ->
                    Toast.makeText(context, effect.message.asString(context), Toast.LENGTH_SHORT).show()

                is UserProfileSideEffect.ShowError ->
                    Toast.makeText(context, effect.message.asString(context), Toast.LENGTH_SHORT).show()
            }
        }
    }

    UserProfileScreenContent(
        state = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun UserProfileScreenContent(
    state: UserProfileUiState,
    onEvent: (UserProfileScreenEvent) -> Unit
) {

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopbarComponent(
                screenName = stringResource(R.string.perfil),
                onBackClick = { onEvent(UserProfileScreenEvent.OnBackClick) },
                onUserProfileClick = {},
                onLogoutClick = { onEvent(UserProfileScreenEvent.OnLogoutClick) },
                iconMoreOptions = true
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        val paddingHorizontalValue = if (isPortrait) 0 else 60

        Column(
            modifier = Modifier
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
                .padding(horizontal = paddingHorizontalValue.dp)
                .navigationBarsPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (state.showPhotoPreview && state.photoUrl != null) {
                Dialog(onDismissRequest = {
                    onEvent(UserProfileScreenEvent.OnClosePhotoPreview)
                }) {

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                onEvent(UserProfileScreenEvent.OnClosePhotoPreview)
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        AsyncImage(
                            model = state.photoUrl,
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(280.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            if (state.showDeleteDialog) {

                DeleteDialog(
                    title = stringResource(R.string.excluir_conta),
                    text = stringResource(R.string.tem_certeza_que_deseja_excluir_sua_conta) + stringResource(R.string.todos_os_seus_dados_serao_apagados_permanentemente),
                    onConfirm = {
                        onEvent(UserProfileScreenEvent.OnConfirmDeleteAccount(userId = state.id))
                    }
                ) {
                    onEvent(UserProfileScreenEvent.OnDismissDeleteDialog)
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            LazyColumn(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                item {
                    UserProfilePictureComponent(
                        imageUrl = state.photoUrl,
                        onPhotoClick = { onEvent(UserProfileScreenEvent.OnPhotoClick) },
                        onCameraClick = { onEvent(UserProfileScreenEvent.OnCameraClick) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Text(
                        text = state.fullName,
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                item {
                    Text(
                        text = state.email,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }

                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                            disabledContentColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                            disabledContainerColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            UserProfileFormFieldComponent(
                                icon = Icons.Outlined.Person,
                                title = stringResource(R.string.nome_completo),
                                value = state.fullName,
                                onValueChange = { onEvent(UserProfileScreenEvent.OnFullNameChange(it)) },
                                placeholder = stringResource(R.string.nome_de_usuario)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            UserProfileFormFieldComponent(
                                icon = Icons.Outlined.Email,
                                title = stringResource(R.string.endereco_de_email),
                                value = state.email,
                                onValueChange = {},
                                placeholder = stringResource(R.string.endereco_de_email),
                                enabled = false
                            )

                            HorizontalDivider(
                                thickness = 1.dp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 18.dp, bottom = 25.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                LeftAlignedTitleWithDescriptionComponent(
                                    title = stringResource(R.string.total_de_memorias),
                                    description = stringResource(R.string.memorias_que_voce_criou_no_memora),
                                    modifier = Modifier.weight(4f)
                                )

                                Text(
                                    text = state.totalMemories.toString(),
                                    textAlign = TextAlign.End,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontSize = 36.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                item {
                    LeftAlignedTitleWithDescriptionComponent(
                        icon = Icons.Outlined.Settings,
                        title = stringResource(R.string.privacidade_e_seguranca),
                        description = stringResource(R.string.msg_excluir_dados_da_conta),
                        modifier = Modifier.padding(top = 40.dp, bottom = 16.dp)
                    )
                }

                item {
                    LeftAlignedButtonComponent(
                        text = stringResource(R.string.excluir_meus_dados),
                        icon = Icons.Default.Delete,
                        onClick = { onEvent(UserProfileScreenEvent.OnDeleteMyDataClick) },
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 48.dp)
                    )
                }

                item {
                    FilledButtonComponent(
                        text = stringResource(R.string.salvar_alteracoes),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background,
                        onClick = { onEvent(UserProfileScreenEvent.OnSaveChanges) }
                    )
                }
            }
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
private fun UserProfileScreenContentView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            UserProfileScreenContent(
                state = UserProfileUiState(
                    fullName = "Elena Rodriguez",
                    email = "elena.rodriguez@memora.io",
                    totalMemories = 24
                )
            ) { }
        }
    }
}