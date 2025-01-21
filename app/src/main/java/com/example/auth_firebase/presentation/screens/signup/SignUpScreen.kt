package com.example.auth_firebase.presentation.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.auth_firebase.R
import com.example.auth_firebase.presentation.common.components.EmailField
import com.example.auth_firebase.presentation.common.components.PasswordField
import com.example.auth_firebase.presentation.common.components.PasswordFieldWithConditions
import com.example.auth_firebase.presentation.ui.theme.PrimaryColor
import com.example.auth_firebase.presentation.ui.theme.SecondaryColor
import com.example.auth_firebase.presentation.ui.theme.gray1
import com.example.auth_firebase.presentation.ui.theme.gray2

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit,
    onSignInClickNav: () -> Unit,
    modifier: Modifier = Modifier
) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val uiState by signUpViewModel.uiState.collectAsStateWithLifecycle()
    val isAccountCreated by signUpViewModel.isAccountCreated.collectAsStateWithLifecycle()

    LaunchedEffect(isAccountCreated) {
        if (isAccountCreated) {
            onSignUpClick()
            signUpViewModel.resetIsAccountCreated()
        }
    }

    val onSignUpClickMemoized: () -> Unit = remember { signUpViewModel::createAccount }
    val onEmailChange: (String) -> Unit = remember { signUpViewModel::onEmailChange }
    val onPasswordChange: (String) -> Unit = remember { signUpViewModel::onPasswordChange }
    val onConfirmPasswordChange: (String) -> Unit =
        remember { signUpViewModel::onConfirmPasswordChange }
    val onSignInClick: () -> Unit = remember { onSignInClickNav }

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onConfirmPasswordChange = onConfirmPasswordChange,
        onSignUpClick = onSignUpClickMemoized,
        onSignInClick = onSignInClick
    )
}

@Composable
fun SignUpScreenContent(
    uiState: SignUpState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(top = 64.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.create_your_account),
            style = MaterialTheme.typography.bodyLarge,
            color = PrimaryColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "Join App",
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        EmailField(
            value = uiState.email,
            onNewValue = onEmailChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)

        )

        Spacer(modifier = Modifier.padding(4.dp))

        PasswordFieldWithConditions(
            value = uiState.password,
            placeholder = R.string.password,
            onNewValue = onPasswordChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.padding(4.dp))

        PasswordField(
            value = uiState.confirmPassword,
            placeholder = R.string.confirm_password,
            onNewValue = onConfirmPasswordChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                PrimaryColor
            )
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = "or Sign up with",
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .border(1.dp, gray2, MaterialTheme.shapes.extraLarge),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            contentPadding = PaddingValues(0.dp) // Remove default padding
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google), // Replace with your desired icon
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Add spacing between icon and text
                Text(
                    text = stringResource(R.string.sign_up_with_google),
                    color = gray1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 16.dp)
                .border(1.dp, gray2, MaterialTheme.shapes.extraLarge),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            contentPadding = PaddingValues(0.dp) // Remove default padding
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_facebook),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp)) // Add spacing between icon and text
                Text(
                    text = stringResource(R.string.sign_up_with_facebook),
                    color = gray1,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            modifier = Modifier
                .clickable(
                    onClick = {
                        onSignInClick()
                    }
                )
                .align(Alignment.CenterHorizontally),
            text = buildAnnotatedString {
                append(stringResource(R.string.already_have_an_account_sign_in))
                withStyle(
                    style = SpanStyle(color = PrimaryColor)
                )
                {
                    append(" Sign in")
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreenContent(
        uiState = SignUpState(),
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPasswordChange = {},
        onSignUpClick = {},
        onSignInClick = {}
    )
}
