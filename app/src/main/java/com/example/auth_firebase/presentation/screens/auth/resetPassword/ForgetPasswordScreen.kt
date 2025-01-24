package com.example.auth_firebase.presentation.screens.auth.resetPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.auth_firebase.R
import com.example.auth_firebase.presentation.common.components.EmailField
import com.example.auth_firebase.presentation.ui.theme.PrimaryColor
import com.example.auth_firebase.presentation.ui.theme.gray1

@Composable
fun ForgetPasswordScreen(
    onSendClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val viewModel: ForgetPasswordViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    val onBackClickMemoized: () -> Unit = remember { onBackClick }
    val onSendClickMemoized: () -> Unit = remember { viewModel::sendResetPasswordEmail }
    val onEmailChange: (String) -> Unit = remember { viewModel::onEmailChange }

    LaunchedEffect(uiState.isEmailSent) {
        if (uiState.isEmailSent) {
            onSendClick()
        }
    }
    ForgetPasswordContentScreen(
        uiState = uiState,
        onSendClick = onSendClickMemoized,
        onBackClick = onBackClickMemoized,
        onEmailChange = onEmailChange,
    )
}

@Composable
fun ForgetPasswordContentScreen(
    uiState: ForgetPasswordState,
    onSendClick: () -> Unit,
    onBackClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 46.dp)
            .padding(horizontal = 16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = modifier
                .align(Alignment.Start)
                .clickable(onClick = onBackClick)
        )

        Text(
            modifier = modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.forget_password),
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.please_enter_the_email_address),
            style = MaterialTheme.typography.bodyMedium,
            color = gray1,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.forgot_password),
            contentDescription = null,
            modifier = modifier
                .padding(top = 16.dp)
        )

        EmailField(
            value = uiState.email,
            onNewValue = onEmailChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)

        )

        Button(
            onClick = {
                onSendClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(
                PrimaryColor
            )
        ) {
            Text(
                text = stringResource(R.string.send_email),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgetPasswordScreenPreview() {
    ForgetPasswordContentScreen(
        onSendClick = {},
        onBackClick = {},
        onEmailChange = {},
        uiState = ForgetPasswordState()
    )
}
