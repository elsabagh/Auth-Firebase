package com.example.auth_firebase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth_firebase.presentation.common.snackbar.SnackBarManager
import com.example.auth_firebase.presentation.common.snackbar.SnackBarMessage.Companion.toSnackBarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ContainerAppViewModel : ViewModel() {
    fun launchCatching(snackBar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    SnackBarManager.showMessage(throwable.toSnackBarMessage())
                }
            },
            block = block
        )
}