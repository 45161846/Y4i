package com.example.remotelogin.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remotelogin.util.isValidEmail
import com.example.russian.R
import com.example.russian.login.draw.comp.TextInput

@Composable
fun ForgetPassword(
    sendEmail: (String) -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        
        var emailText by remember {
            mutableStateOf("")
        }

        Spacer(Modifier.weight(2F))

        TextInput(
            text = emailText,
            hint = stringResource(R.string.email_placeholder),
            lastInColumn = true,
            checkCorrect = {
                it.isEmpty() || isValidEmail(it)
            },
            incorrectInputText = stringResource(R.string.error_email_format_incorrect)
        ) {
            emailText = it
        }

        Spacer(Modifier.weight(1F))

        TextButton(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            onClick = {
                sendEmail(emailText)
            },
        ){
            Text(
                "Выслать код",
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
        }

        Spacer(Modifier.weight(1F))

    }
}

@Preview
@Composable
private fun Preview(){
    ForgetPassword {

    }
}