package com.example.russian.architectured.todo

import com.example.russian.architectured.settings.SettingActions
import com.example.russian.architectured.settings.SettingsStatesHolder
import com.example.russian.architectured.wrappers.GameSettings
import com.example.russian.main.ui.draw.settings.StatDisplaySetting

val fakeSettingActions = SettingActions(
    {},{},{},{},{}, {}
)

val testSettingState = SettingsStatesHolder(
    GameSettings(true, false),
    statDisplaySetting = StatDisplaySetting(false, true, true),
    0.5F
)