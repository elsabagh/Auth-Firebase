package com.example.auth_firebase.presentation.screens.auth.signin

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_firebase.R
import com.example.auth_firebase.domain.repository.AccountRepository
import com.example.auth_firebase.presentation.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    private val email
        get() = _uiState.value.email

    private val password
        get() = _uiState.value.password

    private val _isSignInSucceeded = MutableStateFlow(false)
    val isSignInSucceeded: StateFlow<Boolean> = _isSignInSucceeded.asStateFlow()

    private var _isAccountReady = MutableStateFlow(false)
    val isAccountReady: StateFlow<Boolean> = _isAccountReady


    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password
        )
    }

    private fun String.isEmailValid() =
        this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()


    fun signInToAccount() {
        if (!email.isEmailValid()) {
            SnackBarManager.showMessage(R.string.email_error)
            return
        }
        if (password.isBlank()) {
            SnackBarManager.showMessage(R.string.password_error)
            return
        }
        viewModelScope.launch {
            try {
                accountRepository.authenticate(email, password)
                _isSignInSucceeded.value = true
            } catch (e: Exception) {
                if (e.message?.contains("The password is invalid or the user does not have a password") == true) {
                    SnackBarManager.showMessage(R.string.invalid_password_error)
                } else {
                    SnackBarManager.showMessage(R.string.authentication_failed)
                }
            }
        }
    }

    fun createAnonymousAccount() {
        viewModelScope.launch {
            try {
                accountRepository.createAnonymousAccount()
                _isSignInSucceeded.value = true
            } catch (e: Exception) {
                SnackBarManager.showMessage(R.string.authentication_failed)
            }
        }
    }

    fun startTheApp() {
        viewModelScope.launch {
            if (accountRepository.isUserSignedIn) {
                _isAccountReady.value = true
            }
        }
    }

    fun resetIsSignInSucceeded() {
        _isSignInSucceeded.value = false
    }

}