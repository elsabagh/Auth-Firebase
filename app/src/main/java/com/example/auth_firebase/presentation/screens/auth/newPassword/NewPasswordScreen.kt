package com.example.auth_firebase.presentation.screens.auth.newPassword

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_firebase.R
import com.example.auth_firebase.presentation.common.components.PasswordField
import com.example.auth_firebase.presentation.ui.theme.PrimaryColor
import com.example.auth_firebase.presentation.ui.theme.gray1

@Composable
fun NewPasswordScreen(
    onSavaClick: () -> Unit,
    onBackClick: () -> Unit,
) {

    val onBackClickMemoized: () -> Unit = remember { onBackClick }
    val onSavaClickMemoized: () -> Unit = remember { onSavaClick }


    NewPasswordContentScreen(
        onSavaClick = onSavaClickMemoized,
        onBackClick = onBackClickMemoized,
        onPasswordChange = {},
        onConfirmPasswordChange = {},
    )

}

@Composable
fun NewPasswordContentScreen(
    onSavaClick: () -> Unit,
    onBackClick: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 46.dp)
            .padding(horizontal = 16.dp),
    ) {
        Text(
            modifier = modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.reset_your_password),
            style = MaterialTheme.typography.bodyMedium,
            color = PrimaryColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = modifier
                .padding(top = 4.dp)
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.please_enter_new_password),
            style = MaterialTheme.typography.bodyMedium,
            color = gray1,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.image_reset_pass),
            contentDescription = null,
            modifier = modifier
                .padding(top = 16.dp)
        )

        PasswordField(
            value = "",
            placeholder = R.string.password,
            onNewValue = onPasswordChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)

        )

        PasswordField(
            value = "",
            placeholder = R.string.confirm_password,
            onNewValue = onConfirmPasswordChange,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)

        )

        Button(
            onClick = onSavaClick,
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

        Text(
            text = buildAnnotatedString {
                append("Back to ")
                withStyle(
                    style = SpanStyle(color = PrimaryColor)
                ) {
                    append(stringResource(R.string.sign_in))
                }
            },
            modifier = modifier
                .padding(top = 16.dp)
                .clickable { onBackClick() },
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewPasswordPreview() {
    NewPasswordScreen(
        onSavaClick = {},
        onBackClick = {},
    )
}