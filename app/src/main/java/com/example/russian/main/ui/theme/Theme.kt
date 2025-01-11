package com.example.russian.main.ui.theme

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.russian.architectured.MainNavigationActions
import com.example.russian.architectured.ScreenOverView
import com.example.russian.architectured.settings.SettingsPreview
import com.example.russian.architectured.settings.SettingsScreen
import com.example.russian.architectured.settings.SettingsStatesHolder
import com.example.russian.architectured.stats.StatsPreview
import com.example.russian.architectured.stats.TopBarState
import com.example.russian.architectured.todo.fakeSettingActions

private val DarkColorScheme = darkColorScheme(

    surface = DarkGrey,
    onSurface = White,

    primary = LightGrey69,
    onPrimary = White,
    secondary = LightBlack,
    secondaryContainer = LightBlack,
    onSecondaryContainer = TransparentBlack,
    onSecondary = White,
    tertiaryContainer = LightGreyBB,
    tertiary = LightBlue
)

private val LightColorScheme: ColorScheme = lightColorScheme(

//    surface = DarkGrey,
//    onSurface = White,
//
//    primary = LightGrey69,
//    onPrimary = White,
//    secondary = LightBlack,
//    secondaryContainer = LightBlack,
//    onSecondaryContainer = TransparentBlack,
//    onSecondary = White,
//    tertiaryContainer = LightGreyBB,
//    tertiary = LightBlue

)

@Composable
fun MainTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme
    ) {
        content()
    }
}

@Composable
fun RussianTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Settings() {
    RussianTheme {
        ScreenOverView(
            MainNavigationActions(rememberNavController())
        ) { padd, _ ->
            SettingsPreview(padd)
        }
    }
}

@Preview(name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Stats() {
    RussianTheme {
        ScreenOverView(
            MainNavigationActions(rememberNavController()),
            topBarStatus = TopBarState.Show({}, {})
        ) { padd, _ ->
            StatsPreview(padd)
        }
    }
}