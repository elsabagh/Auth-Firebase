package com.example.auth_firebase.presentation.screens.auth.resetPassword

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
class ForgetPasswordViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgetPasswordState())
    val uiState: StateFlow<ForgetPasswordState> = _uiState.asStateFlow()

    private var _email: String = ""

    fun onEmailChange(email: String) {
        _email = email
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun sendResetPasswordEmail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                accountRepository.sendPasswordResetEmail(_email)
                _uiState.value = _uiState.value.copy(isEmailSent = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            } finally {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }
}