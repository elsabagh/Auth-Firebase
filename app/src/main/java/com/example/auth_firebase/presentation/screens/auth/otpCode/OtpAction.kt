package com.example.auth_firebase.presentation.screens.auth.otpCode

sealed interface OtpAction {
    data class OnEnterNumber(val number: Int?, val index: Int): OtpAction
    data class OnChangeFieldFocused(val index: Int): OtpAction
    data object OnKeyboardBack: OtpAction
}