package com.example.russian.login.draw.comp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AuthScreenShimmer(
    showSimmer: Boolean
){
    Spacer(
        Modifier
            .fillMaxSize()
            .animateContentSize { initialValue, targetValue ->

            }
    )
}