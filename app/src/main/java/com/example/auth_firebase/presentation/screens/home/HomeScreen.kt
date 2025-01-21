package com.example.auth_firebase.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreen(
    onSignOutClick: () -> Unit,
    onDeleteMyAccountClick: () -> Unit
) {

    val homeViewModel: HomeViewmodel = hiltViewModel()
    val isAccountSignedOut by homeViewModel.isAccountSignedOut.collectAsStateWithLifecycle()
    val isAccountDeleted by homeViewModel.isAccountDeleted.collectAsStateWithLifecycle()

    LaunchedEffect(
        isAccountSignedOut || isAccountDeleted
    ) {
        when {
            isAccountSignedOut -> {
                onSignOutClick()
            }

            isAccountDeleted -> {
                onDeleteMyAccountClick()
            }
        }
    }
    val onSignOutClickMemoized: () -> Unit = remember {
        homeViewModel::signOutFromAccount
    }
    val onDeleteMyAccountClickMemoized: () -> Unit = remember {
        homeViewModel::deleteAccount
    }

    HomeScreenContent(
        onSignOutClick = onSignOutClickMemoized,
        onDeleteMyAccountClick = onDeleteMyAccountClickMemoized
    )


}

@Composable
fun HomeScreenContent(
    onSignOutClick: () -> Unit,
    onDeleteMyAccountClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Home Screen")

        Button(
            modifier = Modifier.padding(top = 24.dp),
            onClick = onSignOutClick
        ) {
            Text(text = "Sign Out")
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Button(onClick = onDeleteMyAccountClick) {
            Text(text = "Delete Account")
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onSignOutClick = {},
        onDeleteMyAccountClick = {}
    )
}