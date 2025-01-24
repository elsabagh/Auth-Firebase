package com.example.auth_firebase.presentation.screens.auth.newPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_firebase.domain.repository.AccountRepository
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

    private val _isPasswordChanged = MutableStateFlow(false)
    val isPasswordChanged: StateFlow<Boolean> = _isPasswordChanged.asStateFlow()

    private val _isPasswordConfirmed = MutableStateFlow(false)
    val isPasswordConfirmed: StateFlow<Boolean> = _isPasswordConfirmed.asStateFlow()


    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password
        )
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = confirmPassword
        )
    }

    fun changePassword() {
        if (password.isBlank()) {
            return
        }
        if (confirmPassword.isBlank()) {
            return
        }
        if (password != confirmPassword) {
            return
        }
        viewModelScope.launch {
            accountRepository.changePassword(password)
            _isPasswordChanged.value = true
        }
    }


}