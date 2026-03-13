package com.example.memoraapp.presentation.ui.screens.userprofile

interface UserProfileScreenEvent {

    data class OnInit(val userId: String) : UserProfileScreenEvent
    data object OnBackClick : UserProfileScreenEvent
    data class OnFullNameChange(val value: String) : UserProfileScreenEvent
    data class OnDeleteMyDataClick(val userId: String) : UserProfileScreenEvent
    data object OnSaveChanges : UserProfileScreenEvent
    data object OnLogoutClick : UserProfileScreenEvent
    data object OnPhotoClick : UserProfileScreenEvent
    data object OnClosePhotoPreview : UserProfileScreenEvent
    data object OnCameraClick : UserProfileScreenEvent
}