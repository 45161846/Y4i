package com.example.russian.main.settings

import com.example.russian.main.wrappers.GameSettings
import kotlinx.serialization.Serializable

data class SettingsStatesHolder(
    val gameSettings: GameSettings,
    val statDisplaySetting: StatDisplaySetting,
    val winrate: Float
)

data class SettingActions(
    val onSoundClick: (Boolean) -> Unit,
    val onVibrationClick: (Boolean) -> Unit,
    val onIconChangeClick: (Boolean) -> Unit,
    val onWinrateChangeClick: (Boolean) -> Unit,
    val onIndicatorChangeClick: (Boolean) -> Unit,
    val onSliderChange: (Float) -> Unit
)

data class TestStatsCardState(
    val winrate: Double,
    val displaySetting: StatDisplaySetting
)

@Serializable
data class StatDisplaySetting(
    val showWinrate: Boolean,
    val showTypeIcon: Boolean,
    val showIndicator: Boolean
){
    companion object{
        fun Empty() = StatDisplaySetting(
            false, false, false
        )
    }
}