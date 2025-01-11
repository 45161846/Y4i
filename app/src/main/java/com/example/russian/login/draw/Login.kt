package com.example.russian.login.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.russian.login.draw.comp.AuthProcessPlaceholder
import com.example.russian.login.draw.comp.TextInput
import com.example.russian.R
import com.example.remotelogin.util.changePlaceholder
import com.example.remotelogin.util.isValidEmail
import com.example.remotelogin.util.isValidPassword
import com.example.remotelogin.wrappers.Credentials
import com.example.remotelogin.wrappers.placeholder.AuthProcessPlaceholder
import com.example.russian.main.ui.theme.LightBlue

@Composable
fun Login(
    processPlaceholder: MutableState<AuthProcessPlaceholder>,
    login: String = "",
    onForgetPassword: () -> Unit,
    onGoToCreate: () -> Unit,
    onSignIn: (Credentials) -> Unit,
    onNoAccountContinue: () -> Unit
) {

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
            mutableStateOf(login)
        }

        TextInput(
            text = emailText,
            hint = stringResource(R.string.email_placeholder),
            hide = false,
            checkCorrect = {
                it.isEmpty() || isValidEmail(it)
            },
            incorrectInputText = stringResource(R.string.error_email_or_login_format_incorrect)
        ) {
            emailText = it
            changePlaceholder(processPlaceholder)
        }

        var passwordText by rememberSaveable {
            mutableStateOf("")
        }
        TextInput(
            text = passwordText,
            hint = stringResource(R.string.password_placeholder),
            hide = true,
            lastInColumn = true,
            checkCorrect = {
                it.isEmpty() || isValidPassword(it)
            },
            incorrectInputText = stringResource(R.string.error_password_format_incorrect)
        ) {
            passwordText = it
            changePlaceholder(processPlaceholder)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 6.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val smallTextColor = Color.White
            val textSize = 16.sp

            Text(
                text = stringResource(R.string.reset_password),
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onForgetPassword()
                    },
                color = smallTextColor,
                fontSize = textSize
            )

            Text(
                text = stringResource(R.string.create_account),
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onGoToCreate()
                    },
                color = smallTextColor,
                fontSize = textSize
            )
        }

        AuthProcessPlaceholder(
            Modifier.weight(2F, true),
            processPlaceholder
        )

        Button(
            modifier = Modifier.wrapContentSize(),
            onClick = {
                if(isValidEmail(emailText) && isValidPassword(passwordText)){
                    onSignIn(
                        Credentials.Valid(
                            login = "",
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
                text = stringResource(R.string.sign_in),
                fontSize = 16.sp
            )
        }

        val unwantedTextColor = Color.LightGray

        Text(
            text = stringResource(R.string.continue_no_account),
            color = unwantedTextColor,
            modifier = Modifier.clickable {
                onNoAccountContinue()
            },
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.weight(1F))
    }
}

@Preview
@Composable
private fun Preview() {
    val placeholder: MutableState<AuthProcessPlaceholder> = remember {
        mutableStateOf(AuthProcessPlaceholder.None)
    }
    Login(
        placeholder,"",
        {},{},{}, {}
    )
}