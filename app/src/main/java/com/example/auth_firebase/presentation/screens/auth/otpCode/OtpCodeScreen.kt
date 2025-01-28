package com.example.auth_firebase.presentation.screens.auth.otpCode

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth_firebase.R
import com.example.auth_firebase.presentation.screens.auth.otpCode.components.OtpInputField
import com.example.auth_firebase.presentation.screens.auth.otpCode.components.VerifyDialog
import com.example.auth_firebase.presentation.ui.theme.PrimaryColor
import com.example.auth_firebase.presentation.ui.theme.gray1
import kotlinx.coroutines.launch

@Composable
fun OtpCodeScreen(
    email: String,
    onVerifyOtpCodeClick: () -> Unit,
    onResendOtpCodeClick: () -> Unit,
    onBackClick: () -> Unit,
    onConfirmDialogClick: () -> Unit
) {

    val viewModel: OtpViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) } // State to manage dialog visibility

    val onVerifyOtpCodeClickMemoized = {
        showDialog = true // Show dialog when the verify button is clicked
        onVerifyOtpCodeClick()
    }
    val onResendOtpCodeClickMemoized: () -> Unit = remember { onResendOtpCodeClick }
    val onBackClickMemoized: () -> Unit = remember { onBackClick }
    val focusRequesters = List(state.code.size) { FocusRequester() }
    val onOptCodeChangeMemoized: (Int, Int?) -> Unit = { index, number ->
        coroutineScope.launch {
            viewModel.onAction(OtpAction.OnEnterNumber(number, index))
        }
    }
    val onFocusChangedMemoized: (Int, Boolean) -> Unit = { index, isFocused ->
        coroutineScope.launch {
            if (isFocused) {
                viewModel.onAction(OtpAction.OnChangeFieldFocused(index))
            }
        }
    }
    val onKeyboardBackMemoized: () -> Unit = {
        coroutineScope.launch {
            viewModel.onAction(OtpAction.OnKeyboardBack)
        }
    }

    OtpCodeContentScreen(
        email = email,
        state = state,
        onVerifyOtpCodeClick = onVerifyOtpCodeClickMemoized,
        onResendOtpCodeClick = onResendOtpCodeClickMemoized,
        onBackClick = onBackClickMemoized,
        onOtpCodeChange = onOptCodeChangeMemoized,
        onFocusChanged = onFocusChangedMemoized,
        onKeyboardBack = onKeyboardBackMemoized,
        focusRequesters = focusRequesters
    )

    if (showDialog) {
        VerifyDialog(onConfirm = {
            showDialog = false
            onConfirmDialogClick() // Navigate to SignInDestination when dialog is confirmed
        })
    }
}


@Composable
fun OtpCodeContentScreen(
    email: String,
    state: OtpState,
    onVerifyOtpCodeClick: () -> Unit,
    onResendOtpCodeClick: () -> Unit,
    onBackClick: () -> Unit,
    onOtpCodeChange: (Int, Int?) -> Unit,
    onFocusChanged: (Int, Boolean) -> Unit,
    onKeyboardBack: () -> Unit,
    focusRequesters: List<FocusRequester>, // Pass the list of FocusRequester objects
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 46.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
            fontWeight = FontWeight.Medium
        )

        Image(
            painter = painterResource(id = R.drawable.image_otp),
            contentDescription = null,
            modifier = modifier
                .padding(top = 16.dp)
                .clickable(onClick = onBackClick)
        )

        Text(
            modifier = modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            text = "Enter OTP sent to $email",
            style = MaterialTheme.typography.bodyMedium,
            color = gray1,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
        ) {
            state.code.forEachIndexed { index, number ->
                OtpInputField(
                    number = number,
                    focusRequester = focusRequesters[index],
                    onFocusChanged = { isFocused ->
                        onFocusChanged(index, isFocused)
                    },
                    onNumberChanged = { onOtpCodeChange(index, it) },
                    onKeyboardBack = onKeyboardBack,
                    onDeleteEmpty = {
                        // Move focus to the previous OtpInputField if it exists
                        if (index > 0) {
                            focusRequesters[index - 1].requestFocus()
                        }
                    },
                    onNextFocus = {
                        // Move focus to the next OtpInputField if it exists
                        if (index < focusRequesters.size - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    },
                    modifier = Modifier.size(40.dp)
                )
                if (index < state.code.size - 1) {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
        ) {
            Text(
                modifier = modifier,
                text = stringResource(R.string.don_t_receive_otp),
                style = MaterialTheme.typography.bodyMedium,
                color = gray1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                modifier = modifier
                    .padding(start = 4.dp)
                    .clickable(onClick = onResendOtpCodeClick),
                text = stringResource(R.string.resend_otp),
                style = MaterialTheme.typography.bodyMedium,
                color = gray1,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = {
                onVerifyOtpCodeClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(
                PrimaryColor
            )
        ) {
            Text(
                text = stringResource(R.string.verify_email),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OtpCodeScreenPreview() {
    OtpCodeScreen(
        onVerifyOtpCodeClick = {},
        onResendOtpCodeClick = {},
        onBackClick = {},
        onConfirmDialogClick = {},
        email = ""
    )
}