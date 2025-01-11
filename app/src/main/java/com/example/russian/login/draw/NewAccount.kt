package com.example.remotelogin.draw


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.R
import com.example.remotelogin.util.changePlaceholder
import com.example.remotelogin.util.isValidEmail
import com.example.remotelogin.util.isValidLogin
import com.example.remotelogin.util.isValidPassword
import com.example.remotelogin.wrappers.Credentials
import com.example.remotelogin.wrappers.FieldDescription
import com.example.remotelogin.wrappers.placeholder.AuthProcessPlaceholder
import com.example.russian.login.draw.comp.AuthProcessPlaceholder
import com.example.russian.login.draw.comp.DescriptionDialog
import com.example.russian.login.draw.comp.TextInput
import com.example.russian.main.ui.theme.LightBlue

@Composable
fun NewAccount(
    processPlaceholder: MutableState<AuthProcessPlaceholder>,
    onCreate: (Credentials) -> Unit,
) {

    var showDescription: FieldDescription by remember {
        mutableStateOf(FieldDescription.NoDescription)
    }

    Description(
        showDescription,
    ) {
        showDescription = FieldDescription.NoDescription
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(3F, true))

        var emailText by rememberSaveable {
            mutableStateOf("")
        }

        TextInput(
            text = emailText,
            hint = stringResource(R.string.email_placeholder),
            hide = false,
            onQuestion = {
                showDescription = FieldDescription.Email
            },
            checkCorrect = {
                it.isEmpty() || isValidEmail(it)
            },
            incorrectInputText = stringResource(R.string.error_email_format_incorrect)
        ) { emailText = it; changePlaceholder(processPlaceholder) }

        var loginText by rememberSaveable {
            mutableStateOf("")
        }
        TextInput(
            text = loginText,
            hint = stringResource(R.string.login_placeholder),
            hide = false,
            onQuestion = {
                showDescription = FieldDescription.Login
            },
            checkCorrect = {
                it.isEmpty() || isValidLogin(it)
            },
            incorrectInputText = stringResource(R.string.error_login_format_incorrect)
            ) { loginText = it; changePlaceholder(processPlaceholder) }

        var passwordText by rememberSaveable {
            mutableStateOf("")
        }
        TextInput(
            text = passwordText,
            hint = stringResource(R.string.password_placeholder),
            hide = true,
            onQuestion = {
                showDescription = FieldDescription.Password
            },
            checkCorrect = {
                it.isEmpty() || isValidPassword(it)
            },
            incorrectInputText = stringResource(R.string.error_password_format_incorrect)
        ) { passwordText = it; changePlaceholder(processPlaceholder) }

        var passwordConfirmationText by rememberSaveable {
            mutableStateOf("")
        }
        TextInput(
            text = passwordConfirmationText,
            hint = stringResource(R.string.confirm_password_placeholder),
            hide = true,
            lastInColumn = true,
            checkCorrect = { passwordText == passwordConfirmationText },
            incorrectInputText = stringResource(R.string.error_password_mismatch)
        ) {
            passwordConfirmationText = it
            changePlaceholder(processPlaceholder)
        }

        AuthProcessPlaceholder(
            Modifier.weight(2F, true),
            processPlaceholder
        )

        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                if (
                    isValidEmail(emailText) &&
                    isValidLogin(loginText) &&
                    isValidPassword(passwordText) &&
                    passwordText == passwordConfirmationText
                ) {
                    onCreate(
                        Credentials.Valid(
                            login = loginText,
                            email = emailText,
                            password = passwordText
                        )
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightBlue
            )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 32.dp),
                text = stringResource(R.string.create_account),
                fontSize = 16.sp
            )
        }

        Spacer(Modifier.weight(1F))
    }
}

@Composable
private fun Description(
    description: FieldDescription,
    onDismiss: () -> Unit
) {
    if (description is FieldDescription.NoDescription) return

    DescriptionDialog(
        onDismissRequest = onDismiss,
        dialogTitle = dialogTitleId(description)?.let { stringResource(it) } ?: "",
        dialogText = dialogTextId(description)?.let { stringResource(it) } ?: "",
        icon = dialogIconId(description)?.let { painterResource(it) }
    )

}

private fun dialogTitleId(description: FieldDescription): Int? {
    return when (description) {
        is FieldDescription.NoDescription -> null
        is FieldDescription.Email -> R.string.email_placeholder
        is FieldDescription.Login -> R.string.login_placeholder
        is FieldDescription.Password -> R.string.password_placeholder
    }
}

private fun dialogTextId(description: FieldDescription): Int? {
    return when (description) {
        is FieldDescription.NoDescription -> null
        is FieldDescription.Email -> R.string.email_description
        is FieldDescription.Login -> R.string.login_description
        is FieldDescription.Password -> R.string.password_description
    }
}

private fun dialogIconId(description: FieldDescription): Int? {
    return when (description) {
        is FieldDescription.NoDescription -> null
        is FieldDescription.Email -> R.drawable.email_icon
        is FieldDescription.Login -> R.drawable.user_icon
        is FieldDescription.Password -> R.drawable.password_icon
    }
}

@Preview
@Composable
private fun Preview() {
    val placeholder: MutableState<AuthProcessPlaceholder> = remember {
        mutableStateOf(AuthProcessPlaceholder.None)
    }
    NewAccount(
        placeholder
    ) {}

}