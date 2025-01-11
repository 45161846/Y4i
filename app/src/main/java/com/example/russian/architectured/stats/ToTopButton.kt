package com.example.russian.architectured.stats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.main.ui.modifier.myToTopButton

@Composable
fun DrawToTopButton(
    listState: LazyListState, onClick: () -> Unit
) {

    val showButton by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 10
        }
    }
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        FloatingActionButton(
            onClick = { onClick() },
            modifier = Modifier.myToTopButton(),
            containerColor = colorResource(id = R.color.dark_background_2),
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_up),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.size(32.dp)
            )
        }

    }
}