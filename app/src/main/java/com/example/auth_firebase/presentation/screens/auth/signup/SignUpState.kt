package com.example.auth_firebase.presentation.screens.auth.signup

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)