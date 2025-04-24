package com.example.russian.main.settings.paragraphs

import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.main.settings.comp.TestCardOptionButton
import com.example.russian.main.theme.AppThemes

@Composable
fun ThemeChoose(
    appTheme: AppThemes,
    changeTheme: (AppThemes) -> Unit
) {
    val darkDisabled = ImageVector.vectorResource(R.drawable.moon_settings_disabled)
    val darkEnabled = ImageVector.vectorResource(R.drawable.moon_settings_enabled)
    val lightDisabled = ImageVector.vectorResource(R.drawable.sun_settings)
    val lightEnabled = ImageVector.vectorResource(R.drawable.sun_settings_enabled)
    val customDisabled = ImageVector.vectorResource(R.drawable.palette_settings_disabled)
    val customEnabled = ImageVector.vectorResource(R.drawable.palette_settings)

    Row {

        OptionButton(
            appTheme == AppThemes.DefaultDark,
            darkEnabled, darkDisabled,
            Modifier
                .padding(end = 4.dp)
                .weight(1F)
                .height(48.dp),
        ) { changeTheme(AppThemes.DefaultDark) }
        OptionButton(
            appTheme == AppThemes.DefaultLight,
            lightEnabled, lightDisabled,
            Modifier
                .padding(end = 4.dp)
                .weight(1F)
                .height(48.dp),
        ) { changeTheme(AppThemes.DefaultLight) }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            OptionCustom(
                appTheme == AppThemes.Custom,
                customEnabled, customDisabled,
                Modifier
                    .padding(end = 4.dp)
                    .weight(1F)
                    .height(48.dp),
            ) { changeTheme(AppThemes.Custom) }
        }
    }
}

@Composable
private fun OptionButton(
    enabled: Boolean,
    enabledIcon: ImageVector,
    disabledIcon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val currentColor = if (enabled)
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f)
    else MaterialTheme.colorScheme.surfaceVariant

    val shape = RoundedCornerShape(20)

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = shape,
        border = BorderStroke(2.dp, currentColor)
    ){
        Icon(
            if (enabled) enabledIcon else disabledIcon,
            "",
            tint = currentColor
        )
    }
}

@Composable
fun OptionCustom(
    enabled: Boolean,
    enabledIcon: ImageVector,
    disabledIcon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val currentColor = if (enabled)
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f)
    else MaterialTheme.colorScheme.surfaceVariant

    val shape = RoundedCornerShape(20)

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = shape,
        border = BorderStroke(2.dp, currentColor)
    ){
        Icon(
            if (enabled) enabledIcon else disabledIcon,
            "",
            tint = if (!enabled) currentColor else Color.Unspecified
        )
    }
}