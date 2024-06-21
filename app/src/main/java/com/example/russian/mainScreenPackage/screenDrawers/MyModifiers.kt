package com.example.russian.mainScreenPackage.screenDrawers

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

fun Modifier.myToTopButton() =
    padding(bottom = 10.dp, end = 10.dp)
        .clip(RoundedCornerShape(8.dp))
