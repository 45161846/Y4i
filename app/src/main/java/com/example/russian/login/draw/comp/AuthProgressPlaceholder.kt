package com.example.russian.login.draw.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.remotelogin.wrappers.placeholder.AuthProcessPlaceholder
import com.example.russian.main.theme.LightRed

@Composable
fun AuthProcessPlaceholder(
    modifier: Modifier,
    processPlaceholder: State<AuthProcessPlaceholder>
){

    val placeholder by remember{
        processPlaceholder
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ){
        placeholder.let {
            when(it){
                is AuthProcessPlaceholder.None -> {}
                is AuthProcessPlaceholder.Error -> Text(it.error, color = LightRed)
                is AuthProcessPlaceholder.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }


    }
}