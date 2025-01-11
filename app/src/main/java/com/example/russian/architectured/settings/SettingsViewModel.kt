package com.example.russian.architectured.settings

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.russian.main.ui.draw.settings.TestStatsCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    sharedPreferences: SharedPreferences,
    state: SavedStateHandle
) : ViewModel() {
    
    private val settings = Settings(sharedPreferences)
//    private val settingFlow = MutableStateFlow()

    val uiStatesHolder = SettingsStatesHolder(
        vibrationState = SwitchState(
            text = "Вибрация",
            MutableStateFlow(settings.vibrationOn)
        ),
        soundState = SwitchState(text = "Звук", MutableStateFlow(settings.soundOn)),
        testStatsCardState = TestStatsCardState(
            winrate = MutableStateFlow(0.5),
            showTypeIcon = MutableStateFlow(settings.showIcon),
            showWinrate = MutableStateFlow(settings.showWinrate),
            showIndicator = MutableStateFlow(settings.showIndicator)
        )
    )
    
    fun settingsActions() = SettingActions(
        onSoundClick = {
            settings.editor.putBoolean(settings.soundKey, it).apply()
            settings.soundOn = it
            uiStatesHolder.soundState.checked.value = it
        },
        onVibrationClick = {
            settings.editor.putBoolean(settings.vibrationsKey, it).apply()
            settings.vibrationOn = it
            uiStatesHolder.vibrationState.checked.value = it
        },
        onSliderChange = {
            uiStatesHolder.testStatsCardState.winrate.value = it.toDouble()
        },
        onWinrateChangeClick = {
            uiStatesHolder.testStatsCardState.showWinrate.value = it
            settings.showWinrate = it
            settings.editor.putBoolean(settings.winrateKey, it).apply()
        },
        onIconChangeClick = {
            uiStatesHolder.testStatsCardState.showTypeIcon.value = it
            settings.showIcon = it
            settings.editor.putBoolean(settings.iconKey, it).apply()
        }, onIndicatorChangeClick = {
            uiStatesHolder.testStatsCardState.showIndicator.value = it
            settings.showIndicator = it
            settings.editor.putBoolean(settings.indicatorKey, it).apply()
        }, onRatingClicked = {

        }, onTelegramClick = {

        }
    )

}