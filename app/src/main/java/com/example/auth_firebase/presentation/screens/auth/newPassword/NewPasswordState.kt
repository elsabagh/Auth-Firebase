package com.example.auth_firebase.presentation.screens.auth.newPassword

data class NewPasswordState(
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isPasswordChanged: Boolean = false
)