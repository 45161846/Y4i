package com.example.russian.architectured.settings

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

data class Settings(
    val sharedPreferences: SharedPreferences,
    val editor: Editor = sharedPreferences.edit(),

    val indicatorKey: String = "indicator",
    var showIndicator: Boolean = sharedPreferences.getBoolean(indicatorKey, true),

    val winrateKey: String = "winrate",
    var showWinrate: Boolean = sharedPreferences.getBoolean(winrateKey, true),

    val iconKey: String = "show_icon",
    var showIcon: Boolean = sharedPreferences.getBoolean(iconKey, false),

    val soundKey: String = "sound",
    var soundOn: Boolean = sharedPreferences.getBoolean(soundKey, true),

    val vibrationsKey: String = "vibration",
    var vibrationOn: Boolean = sharedPreferences.getBoolean(vibrationsKey, true)
)