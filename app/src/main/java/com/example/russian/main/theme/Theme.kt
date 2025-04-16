package com.example.russian.main.theme

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.russian.game.ui.state.AnswerColor
import com.example.russian.main.MainNavigationActions
import com.example.russian.main.ScreenOverView
import com.example.russian.main.settings.SettingsPreview
import com.example.russian.main.stats.StatsPreview
import com.example.russian.main.stats.comp.stats.BottomBarState
import com.example.russian.main.stats.comp.stats.TopBarState

private val DarkColorScheme = darkColorScheme(

    surface = Black11,
    onSurface = WhiteDD,

    surfaceVariant = DarkGrey,
    onSurfaceVariant = WhiteDD,

    primaryContainer = BlackBlue,
    onPrimaryContainer = LightBlue,
    primary = LightBlue,
    inversePrimary = SaturatedYellow,

    secondary = LightBlack,
    onSecondaryContainer = TransparentWhite,
    onSecondary = WhiteDD,

    tertiaryContainer = LightBlueContainer,
    onTertiaryContainer = AbsoluteWhite,
    tertiary = LightBlue,
    onTertiary = AbsoluteWhite
)

private val LightColorScheme: ColorScheme = lightColorScheme(

    surface = AbsoluteWhite,
    onSurface = TransparentBlack,

    surfaceVariant = WhiteEE,
    onSurfaceVariant = Black,

    primaryContainer = WhiteBlue,
    primary = LightBlue,
    inversePrimary = BlackPurple,

    secondary = WhitePurple,
    onSecondaryContainer = WhiteEE,
    onSecondary = WhiteEE,

    tertiaryContainer = LightBlueContainer,
    onTertiaryContainer = AbsoluteWhite,
    tertiary = LightBlue,
    onTertiary = AbsoluteWhite

)
@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5


@Composable
fun RussianTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val colorScheme = when {
        dynamicColor && darkTheme -> dynamicDarkColorScheme(LocalContext.current)
        dynamicColor && !darkTheme -> dynamicLightColorScheme(LocalContext.current)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CommonTypography,
        content = content
    )
}

@Composable
fun MaterialTheme.answerColor(answerColor: AnswerColor): androidx.compose.ui.graphics.Color {

    return if(answerColor == AnswerColor.CORRECT){
        if(colorScheme.isLight()) LightModeAnswerCorrect else DarkModeAnswerCorrect
    }else if(answerColor == AnswerColor.INCORRECT){
        if(colorScheme.isLight()) LightModeAnswerIncorrect else DarkModeAnswerIncorrect
    } else androidx.compose.ui.graphics.Color.Unspecified

}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Settings() {

    RussianTheme {
        ScreenOverView(
            MainNavigationActions(rememberNavController()),
            TopBarState.Hide,
            BottomBarState.Show,
            rememberPagerState { 2 },
            Modifier
        ) { _->
            SettingsPreview()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun Stats() {

    RussianTheme {
        ScreenOverView(
            MainNavigationActions(rememberNavController()),
            topBarStatus = TopBarState.Show.ShowSearch({}, {}),
            BottomBarState.Show,
            rememberPagerState { 2 },
            Modifier
        ) { _ ->
            StatsPreview()
        }
    }
}