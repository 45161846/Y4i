package com.example.russian.main.theme

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

val CommonTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = family,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
    )
)

val GameButtonTextStyle = TextStyle(
    fontFamily = family,
    fontWeight = FontWeight.Normal,
    fontSize = 28.sp,
    letterSpacing = 0.5.sp,
    color = Color.Black
)