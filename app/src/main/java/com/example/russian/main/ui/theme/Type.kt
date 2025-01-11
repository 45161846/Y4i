package com.example.russian.main.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.russian.R

val family = FontFamily(
    Font(R.font.open_sans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.open_sants_regular, FontWeight.Normal, FontStyle.Normal),
)

// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.3.sp,
        color = Color.Black
    ),
    titleLarge = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color.Black
    ),
    labelSmall = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
)

val GameButtonTextStyle = TextStyle(
    fontFamily = family,
    fontWeight = FontWeight.Normal,
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.5.sp,
    color = Color.Black
)