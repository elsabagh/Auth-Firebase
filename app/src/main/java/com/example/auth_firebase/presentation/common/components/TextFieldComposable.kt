package com.example.auth_firebase.presentation.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auth_firebase.R
import com.example.auth_firebase.presentation.ui.theme.PrimaryColor
import com.example.auth_firebase.presentation.ui.theme.gray1

/**
 * A composable representing an email input field with a leading icon for email.
 *
 * @param value The current value of the email field.
 * @param onNewValue A lambda function to handle the change in value (e.g., updating the email).
 * @param modifier A [Modifier] to customize the appearance of the email field (e.g., padding, size).
 */

@Composable
fun EmailField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text(text = stringResource(R.string.email)) },
        leadingIcon = {
            Image(
                painter = painterResource(R.drawable.icon_mail),
                contentDescription = stringResource(R.string.email),
            )
        },
    )
}

/**
 * A composable representing a password input field with a visibility toggle.
 *
 * @param value The current value of the password field.
 * @param placeholder The resource ID of the placeholder text to be displayed in the password field.
 * @param onNewValue A lambda function to handle the change in value (e.g., updating the password).
 * @param modifier A [Modifier] to customize the appearance of the password field (e.g., padding, size).
 */
@Composable
fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(R.drawable.visibility_on_24)
        else painterResource(R.drawable.visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text(text = stringResource(placeholder)) },
        leadingIcon = {
            Image(
                painter = painterResource(R.drawable.ic_lock),
                contentDescription = stringResource(R.string.password),
            )
        },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    painter = icon,
                    contentDescription = if (isVisible) stringResource(R.string.password_field_end_icon_hide_content_description)
                    else stringResource(R.string.password_field_end_icon_show_content_description)
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}

@Composable
fun PasswordCondition(
    condition: String,
    isValid: Boolean
) {
    val color = if (isValid) PrimaryColor else gray1
    Text(
        text = condition,
        color = color,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp)
    )
}

@Composable
fun PasswordFieldWithConditions(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(R.drawable.visibility_on_24)
        else painterResource(R.drawable.visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    Column() {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = { onNewValue(it) },
            label = { Text(text = stringResource(placeholder)) },
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.ic_lock),
                    contentDescription = stringResource(R.string.password),
                )
            },
            trailingIcon = {
                IconButton(onClick = { isVisible = !isVisible }) {
                    Icon(
                        painter = icon,
                        contentDescription = if (isVisible) stringResource(R.string.password_field_end_icon_hide_content_description)
                        else stringResource(R.string.password_field_end_icon_show_content_description)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = visualTransformation
        )

        val hasUpperCase = value.any { it.isUpperCase() }
        val hasLowerCase = value.any { it.isLowerCase() }
        val hasDigit = value.any { it.isDigit() }
        val hasMinLength = value.length >= 6

        PasswordCondition(condition = "At least one uppercase letter", isValid = hasUpperCase)
        PasswordCondition(condition = "At least one lowercase letter", isValid = hasLowerCase)
        PasswordCondition(condition = "At least one digit", isValid = hasDigit)
        PasswordCondition(condition = "At least 6 characters", isValid = hasMinLength)
    }
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun PasswordFieldWithConditions() {
    PasswordFieldWithConditions(
        value = "",
        placeholder = R.string.password,
        onNewValue = {}
    )

}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun PasswordFieldPreview() {
    PasswordField(value = "", placeholder = R.string.password, onNewValue = {})
}