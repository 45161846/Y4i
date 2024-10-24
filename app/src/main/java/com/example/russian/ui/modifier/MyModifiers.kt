package com.example.russian.ui.modifier

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.myToTopButton() =
    padding(bottom = 10.dp, end = 10.dp)
        .clip(RoundedCornerShape(8.dp))

fun Modifier.clickableWithoutRipple(
    onClick: () -> Unit
) = composed(
    factory = {
        val interactionSource = remember { MutableInteractionSource() }
        this.then(
            Modifier.clickable(
                interactionSource,
                indication = null,
                onClick = { onClick() }
            )
        )
    }
)
