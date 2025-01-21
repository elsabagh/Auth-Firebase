package com.example.auth_firebase.presentation.screens.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_firebase.R
import com.example.auth_firebase.domain.repository.AccountRepository
import com.example.auth_firebase.presentation.common.isEmailValid
import com.example.auth_firebase.presentation.common.isPasswordValid
import com.example.auth_firebase.presentation.common.passwordMatches
import com.example.auth_firebase.presentation.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    private val email
        get() = _uiState.value.email

    private val password
        get() = _uiState.value.password


    private var _isAccountCreated = MutableStateFlow(false)
    val isAccountCreated: StateFlow<Boolean> = _isAccountCreated.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            email = newValue
        )
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            password = newValue
        )
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = newValue
        )
    }

    fun createAccount() {
        if (!email.isEmailValid()) {
            SnackBarManager.showMessage(R.string.email_error)
            return
        }

        if (!password.isPasswordValid()) {
            SnackBarManager.showMessage(R.string.empty_password_error)
            return
        }

        if (!password.passwordMatches(_uiState.value.confirmPassword)) {
            SnackBarManager.showMessage(R.string.password_match_error)
            return
        }
        viewModelScope.launch {
            try {
                accountRepository.createAccount(email, password)
                _isAccountCreated.value = true
            } catch (e: Exception) {
                if (e.message?.contains(R.string.email_already_in_use.toString()) == true) {
                    SnackBarManager.showMessage(R.string.email_error)
                } else {
                    SnackBarManager.showMessage(R.string.account_creation_failed)
                }
            }
        }
    }

    fun resetIsAccountCreated() {
        _isAccountCreated.value = false
    }
}