package com.example.auth_firebase.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_firebase.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    val uiState = accountRepository.currentUser.map {
        HomeState(isAnonymousAccount = it.isAnonymous)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HomeState()
    )

    private val _isAccountSignedOut = MutableStateFlow(false)
    val isAccountSignedOut get() = _isAccountSignedOut

    /**
     * Flag indicating whether the user's account has been deleted.
     */
    private val _isAccountDeleted = MutableStateFlow(false)
    val isAccountDeleted get() = _isAccountDeleted

    fun signOutFromAccount() {
        viewModelScope.launch {
            accountRepository.signOut()
            _isAccountSignedOut.value = true
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            accountRepository.deleteAccount()
            _isAccountDeleted.value = true
        }
    }

    fun resetIsAccountSignedOut() {
        _isAccountSignedOut.value = false
    }

    fun resetIsAccountDeleted() {
        _isAccountDeleted.value = false
    }

}