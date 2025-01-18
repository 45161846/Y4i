package com.example.russian.architectured.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.example.russian.architectured.wrappers.GameSettings
import com.example.russian.main.ui.draw.settings.StatDisplaySetting
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
data class Settings @Inject constructor(
    val sharedPreferences: SharedPreferences
) {
    val editor: Editor = sharedPreferences.edit()

    val indicatorKey: String = "indicator"
    var showIndicator: Boolean = sharedPreferences.getBoolean(indicatorKey, true)

    val winrateKey: String = "winrate"
    var showWinrate: Boolean = sharedPreferences.getBoolean(winrateKey, true)

    val iconKey: String = "show_icon"
    var showIcon: Boolean = sharedPreferences.getBoolean(iconKey, false)

    val soundKey: String = "sound"
    var soundOn: Boolean = sharedPreferences.getBoolean(soundKey, true)

    val vibrationsKey: String = "vibration"
    var vibrationOn: Boolean = sharedPreferences.getBoolean(vibrationsKey, true)
}

@Singleton
data class SettingsChanger @Inject constructor(
    val settings: Settings
){
    val displaySettingsFlow = MutableStateFlow(
        StatDisplaySetting(
            showWinrate = settings.showWinrate,
            showTypeIcon = settings.showIcon,
            showIndicator = settings.showIndicator
        )
    )
    val gameSettingsFlow = MutableStateFlow(GameSettings(
        playSound = settings.soundOn,
        vibrate = settings.vibrationOn
    ))
}

@Singleton
data class SettingsHolder @Inject constructor(
    private val settingsChanger: SettingsChanger
){
    val displaySettingsFlow = settingsChanger.displaySettingsFlow.asStateFlow()
    val gameSettingsFlow = settingsChanger.gameSettingsFlow.asStateFlow()
}