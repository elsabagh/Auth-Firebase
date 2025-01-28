package com.example.auth_firebase.presentation.screens.auth.newPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_firebase.R
import com.example.auth_firebase.domain.repository.AccountRepository
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
class NewPasswordViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewPasswordState())
    val uiState: StateFlow<NewPasswordState> = _uiState.asStateFlow()

    private val password
        get() = _uiState.value.password

    private val confirmPassword
        get() = _uiState.value.confirmPassword

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(password = newPassword)
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newConfirmPassword)
    }

    fun changePassword() {
        if (!password.isPasswordValid()) {
            SnackBarManager.showMessage(R.string.invalid_password_error)
            return
        }

        if (!password.passwordMatches(confirmPassword)) {
            SnackBarManager.showMessage(R.string.password_match_error)
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                accountRepository.changePassword(password)
                _uiState.value = _uiState.value.copy(isPasswordChanged = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}