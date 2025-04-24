package com.example.russian.main.todo

import com.example.russian.main.settings.SettingActions
import com.example.russian.main.settings.SettingsStatesHolder
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.theme.AppThemes
import com.example.russian.main.wrappers.GameSettings
val fakeSettingActions = SettingActions(
    {},{},{},{},{}, {}, {}
)

val testSettingState = SettingsStatesHolder(
    GameSettings(true, false),
    statDisplaySetting = StatDisplaySetting(false, true, true),
    0.5F, AppThemes.Custom
)