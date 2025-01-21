package com.example.auth_firebase

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.auth_firebase.presentation.navigation.NavGraph
import com.example.auth_firebase.presentation.ui.theme.AuthFirebaseTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerApp(
    modifier: Modifier = Modifier,
) {

    val appState = rememberAppState()

    AuthFirebaseTheme{
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = appState.scaffoldState.snackbarHostState,
                    modifier = Modifier.padding(8.dp),
                    snackbar = { snackBarData ->
                        Snackbar(snackBarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                    }
                )
            }
        ) { innerPadding ->
            NavGraph(
                appState = appState,
                modifier = modifier.padding(innerPadding)
            )
        }
    }

}