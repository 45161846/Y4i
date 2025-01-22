package com.example.russian.main.prac.details

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StartGameButton(
    show: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {



    androidx.compose.animation.AnimatedVisibility(
        visible = show,
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = "Запустить",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}