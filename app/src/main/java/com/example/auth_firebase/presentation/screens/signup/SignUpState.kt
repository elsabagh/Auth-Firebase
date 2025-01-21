package com.example.auth_firebase.presentation.screens.signup

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)