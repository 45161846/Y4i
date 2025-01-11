package com.example.remotelogin.draw

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.russian.main.ui.theme.LightBlue

@Composable
fun Loading() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(56.dp), color = LightBlue
    )
}