package com.example.auth_firebase.presentation.screens.auth.otpCode

data class OtpState(
    val code: List<Int?> = (1..4).map { null },
    val focusedIndex: Int? = null,
    val isValid: Boolean? = null
)