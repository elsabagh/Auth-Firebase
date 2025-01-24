package com.example.auth_firebase.presentation.screens.auth.resetPassword

data class ForgetPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmailSent: Boolean = false
)