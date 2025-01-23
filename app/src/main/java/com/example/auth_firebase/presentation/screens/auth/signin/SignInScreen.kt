package com.example.auth_firebase.presentation.screens.auth.signin

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
import com.example.auth_firebase.presentation.ui.theme.AuthFirebaseTheme
import com.example.auth_firebase.presentation.ui.theme.PrimaryColor
import com.example.auth_firebase.presentation.ui.theme.SecondaryColor
import com.example.auth_firebase.presentation.ui.theme.gray1
import com.example.auth_firebase.presentation.ui.theme.gray2

@Composable
fun SignInScreen(
    onSignInClick: () -> Unit = {},
    onSignInClickGuest: () -> Unit = {},
    onSignUpClickNav: () -> Unit = {},
    onForgotPasswordClickNav: () -> Unit = {},
    onAppStart: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val signInViewModel: SignInViewModel = hiltViewModel()
    val uiState by signInViewModel.uiState.collectAsStateWithLifecycle()
    val isSignInSucceeded by signInViewModel.isSignInSucceeded.collectAsStateWithLifecycle()
    val isAccountReady by signInViewModel.isAccountReady.collectAsStateWithLifecycle()

    LaunchedEffect(isAccountReady) {
        if (isAccountReady) {
            onAppStart()
        }
    }

    LaunchedEffect(isSignInSucceeded) {
        if (isSignInSucceeded) {
            onSignInClick()
            onSignInClickGuest()
            signInViewModel.resetIsSignInSucceeded()
        }
    }

    val onSignInClickMemoized: () -> Unit = remember { signInViewModel::signInToAccount }
    val onEmailChange: (String) -> Unit = remember { signInViewModel::onEmailChange }
    val onPasswordChange: (String) -> Unit = remember { signInViewModel::onPasswordChange }
    val onSignInClickGuestMemoized: () -> Unit =
        remember { signInViewModel::createAnonymousAccount }
    val onSignUpClickNavMemoized = remember { onSignUpClickNav }
    val onForgotPasswordClickNavMemoized = remember { onForgotPasswordClickNav }
    val onAppStartMemoized = remember { signInViewModel::startTheApp }


    SignInScreenContent(
        uiState = uiState,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onSignInClick = onSignInClickMemoized,
        onSignInClickGuest = onSignInClickGuestMemoized,
        onSignUpClick = onSignUpClickNavMemoized,
        onForgotPasswordClick = onForgotPasswordClickNavMemoized,
        onAppStart = onAppStartMemoized

    )
}

@Composable
fun SignInScreenContent(
    uiState: SignInState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignInClickGuest: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit
) {
    LaunchedEffect(Unit) {
        onAppStart()
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(top = 64.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.sign_in),
            style = MaterialTheme.typography.bodyLarge,
            color = PrimaryColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        EmailField(
            value = uiState.email,
            onNewValue = onEmailChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 46.dp)

        )

        Spacer(modifier = Modifier.padding(4.dp))

        PasswordField(
            value = uiState.password,
            placeholder = R.string.password,
            onNewValue = onPasswordChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            modifier = Modifier.clickable(
                onClick = {
                    onForgotPasswordClick()
                }
            )
                .align(Alignment.End)
                .padding(end = 16.dp),
            text = stringResource(R.string.forgot_password),
            style = MaterialTheme.typography.bodySmall,
            color = SecondaryColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = onSignInClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                PrimaryColor
            )
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            modifier = Modifier
                .clickable(
                    onClick = {
                        onSignInClickGuest()
                    }
                )
                .align(Alignment.CenterHorizontally),
            text = buildAnnotatedString {
                append("Log in as a ")
                withStyle(
                    style = SpanStyle(color = PrimaryColor)
                )
                {
                    append("guest")
                }
                append(" or ")
            },
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
                    text = stringResource(R.string.sign_in_with_google),
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
                    text = stringResource(R.string.sign_in_with_facebook),
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
                        onSignUpClick()
                    }
                )
                .align(Alignment.CenterHorizontally),
            text = buildAnnotatedString {
                append(stringResource(R.string.don_t_have_an_account_sign_up))
                withStyle(
                    style = SpanStyle(color = PrimaryColor)
                )
                {
                    append(" Sign up")
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
fun SignInPreview() {
    AuthFirebaseTheme {
        SignInScreenContent(
            uiState = SignInState(),
            onEmailChange = {},
            onPasswordChange = {},
            onSignInClick = {},
            onSignInClickGuest = {},
            onSignUpClick = {},
            onForgotPasswordClick = {},
            onAppStart = {}
        )
    }

}