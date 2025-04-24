package com.example.russian.main.stats.comp.stats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.example.russian.main.Id
import kotlinx.coroutines.delay

@Composable
fun StatSnackBar(
    taskIdMessage: Id,
    text: String,
    delay: Long,
    modifier: Modifier
){

    var show by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(taskIdMessage, text) {
        if(taskIdMessage.value > 0){
            show = true
            delay(delay)
            show = false
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = show,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Snackbar(
            modifier = Modifier
                .alpha(0.93f)
        ){
            Text(text)
        }
    }
}