package com.example.auth_firebase.presentation.screens.auth.otpCode.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth_firebase.R
import com.example.auth_firebase.presentation.ui.theme.AuthFirebaseTheme
import com.example.auth_firebase.presentation.ui.theme.PrimaryColor
import com.example.auth_firebase.presentation.ui.theme.gray1

@Composable
fun VerifyDialog(
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Handle dismiss if needed */ },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_verify),
                    contentDescription = null,
                    modifier = Modifier.size(124.dp)
                )
                Text(
                    text = stringResource(R.string.your_otp_code_has_been_verified_successfully),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = gray1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        onConfirm()
                    },
                    colors = ButtonDefaults.buttonColors(
                        PrimaryColor
                    )
                ) {
                    Text(
                        text = stringResource(R.string.conferm),
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun VerifyDialogPreview() {
    AuthFirebaseTheme {
        VerifyDialog(onConfirm = {})
    }
}
