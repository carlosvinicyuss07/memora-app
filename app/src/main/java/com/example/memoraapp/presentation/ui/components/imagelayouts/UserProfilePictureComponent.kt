package com.example.memoraapp.presentation.ui.components.imagelayouts

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.memoraapp.presentation.ui.theme.MemoraAppTheme

@Composable
fun UserProfilePictureComponent(
    imageUrl: String? = null,
    onPhotoClick: () -> Unit,
    onCameraClick: () -> Unit
) {

    Box(
        modifier = Modifier.size(110.dp),
        contentAlignment = Alignment.BottomEnd
    ) {

        if (imageUrl == null) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.outline)
                    .clickable { onPhotoClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usário",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.scrim
                )
            }
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .clickable { onPhotoClick() },
                contentScale = ContentScale.Crop
            )
        }

        FloatingActionButton(
            onClick = onCameraClick,
            modifier = Modifier
                .size(34.dp)
                .offset(x = 0.dp, y = 0.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Outlined.PhotoCamera,
                contentDescription = "Tirar foto para perfil"
            )
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
private fun UserProfilePictureComponentView() {
    MemoraAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            UserProfilePictureComponent(
                onPhotoClick = {},
                onCameraClick = {}
            )
        }
    }
}