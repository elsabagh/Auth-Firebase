package com.example.auth_firebase.presentation.screens.auth.otpCode.components

import android.view.KeyEvent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.auth_firebase.presentation.ui.theme.gray1

@Composable
fun OtpInputField(
    number: Int?,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit,
    onDeleteEmpty: () -> Unit, // New callback for backspace on an empty field
    onNextFocus: () -> Unit, // New callback for moving focus to the next field
    modifier: Modifier = Modifier
) {
    var text by remember(number) {
        mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    index = if (number != null) 1 else 0
                )
            )
        )
    }
    var isFocused by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = gray1,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                val newNumber = newText.text
                if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                    onNumberChanged(newNumber.toIntOrNull())
                    text = newText
                    if (newNumber.isNotEmpty()) {
                        onNextFocus() // Move focus to the next field
                    }
                } else if (newNumber.isNotEmpty()) {
                    // If more than one character is entered, only keep the first character
                    val validNumber = newNumber.first().toString()
                    onNumberChanged(validNumber.toIntOrNull())
                    text = TextFieldValue(
                        text = validNumber,
                        selection = TextRange(1)
                    )
                    onNextFocus() // Move focus to the next field
                }
            },
            cursorBrush = SolidColor(gray1),
            singleLine = true,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                fontSize = 24.sp,
                color = gray1
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .padding(4.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged(it.isFocused)
                }
                .onKeyEvent { event ->
                    val didPressDelete = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if (didPressDelete && text.text.isEmpty()) {
                        onDeleteEmpty() // Notify when backspace is pressed on an empty field
                    }
                    false
                },
            decorationBox = { innerBox ->
                innerBox()
                if (!isFocused && number == null) {
                    Text(
                        text = "-",
                        textAlign = TextAlign.Center,
                        color = gray1,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            }
        )
    }
}


@Preview
@Composable
private fun OtpInputFieldPreview() {
    OtpInputField(
        number = null,
        focusRequester = remember { FocusRequester() },
        onFocusChanged = {},
        onKeyboardBack = {},
        onNumberChanged = {},
        modifier = Modifier
            .size(100.dp),
        onNextFocus = {},
        onDeleteEmpty = {}
    )

}