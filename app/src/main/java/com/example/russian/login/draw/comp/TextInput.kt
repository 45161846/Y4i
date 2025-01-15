package com.example.russian.login.draw.comp

import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.R
import com.example.russian.main.ui.theme.LightRed

@Composable
fun TextInput(
    text: String,
    hint: String,
    hide: Boolean = false,
    lastInColumn: Boolean = false,
    onQuestion: (() -> Unit)? = null,
    checkCorrect: (String) -> Boolean,
    incorrectInputText: String = "",
    onTextChange: (String) -> Unit,
) {

    val label = remember{
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

    val shape = RoundedCornerShape(15)

    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.Unspecified, shape)
        .onFocusChanged { focusState ->
            if(focusState.isCaptured.not() && !checkCorrect(text)){
                label.value = incorrectInputText
            }
        }
        .onPreviewKeyEvent {
            if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN){
                focusManager.moveFocus(FocusDirection.Down)
                true
            } else {
                false
            }
        }

    val textFieldBoxColor = MaterialTheme.colorScheme.surfaceVariant
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant

    val colors = TextFieldDefaults.colors(
        focusedTextColor = textColor,
        unfocusedTextColor = textColor,
        disabledTextColor = textColor,
        errorTextColor = textColor,
        focusedContainerColor = textFieldBoxColor,
        unfocusedContainerColor = textFieldBoxColor,
        errorContainerColor = textFieldBoxColor,
        disabledContainerColor = textFieldBoxColor,
        errorIndicatorColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
    )

    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(label.value, color = LightRed, fontSize = 14.sp, modifier = Modifier.padding(bottom = 2.dp, end = 4.dp))

        TextField(
            modifier = modifier
            ,
            value = text,
            onValueChange = {
                onTextChange(it)
                label.value = ""
            },
            shape = shape,
            singleLine = true,
            colors = colors,
            placeholder = {
                Text(hint)
            },
            visualTransformation =
            if (hide) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (hide) KeyboardType.Password else KeyboardType.Unspecified,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    if(!checkCorrect(text)){
                        label.value = incorrectInputText
                    }else{
                        if (lastInColumn){
                            focusManager.clearFocus()
                        }else{
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    }
                }
            ),
            suffix = {
                onQuestion?.let { f ->
                    Image(
                        painter = painterResource(R.drawable.question_circle_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                f()
                            }
                    )
                }
            }
        )
    }
}