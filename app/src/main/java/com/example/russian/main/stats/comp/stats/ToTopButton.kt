package com.example.russian.main.stats.comp.stats

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.russian.R

@Composable
fun DrawToTopButton(
    listState: LazyListState,
    isBottomBarVisible: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    val showButton by remember(isBottomBarVisible) {
        derivedStateOf {
            listState.firstVisibleItemIndex > 7 && !isBottomBarVisible
        }
    }
    AnimatedVisibility(
        modifier = modifier,
        visible = showButton,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        FloatingActionButton(
            onClick = { onClick() },
            modifier = Modifier
                .clip(RoundedCornerShape(15)),
            containerColor = MaterialTheme.colorScheme.primary,
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_up),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary.copy(
                    alpha = 0.87f
                )),
                modifier = Modifier.size(32.dp)
            )
        }

    }
}