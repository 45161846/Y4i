package com.example.russian.architectured.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    changer: SettingsChanger
) : ViewModel() {

    private val settings: Settings = changer.settings

    private val gameSettings = changer.gameSettingsFlow
    private val displaySettingFlow = changer.displaySettingsFlow
    private val winrateFlow = MutableStateFlow(0.5F)

    val uiStatesHolder =
        combine(displaySettingFlow, gameSettings, winrateFlow) { display, game, winrate ->
            SettingsStatesHolder(
                game, display, winrate
            )
        }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                SettingsStatesHolder(
                    changer.gameSettingsFlow.value,
                    changer.displaySettingsFlow.value,
                    0.5F
                )
            )

    init {
//        state[DISPLAY_SETTINGS_KEY] = displaySettingFlow.value
    }

    fun settingsActions() = SettingActions(
        onSoundClick = {
            settings.editor.putBoolean(settings.soundKey, it).apply()
            settings.soundOn = it

            gameSettings.update { state ->
                state.copy(
                    playSound = it
                )
            }
        },
        onVibrationClick = {
            settings.editor.putBoolean(settings.vibrationsKey, it).apply()
            settings.vibrationOn = it
            gameSettings.update { state ->
                state.copy(
                    vibrate = it
                )
            }
        },
        onWinrateChangeClick = {
            settings.showWinrate = it
            settings.editor.putBoolean(settings.winrateKey, it).apply()
            displaySettingFlow.update { state ->
                state.copy(
                    showWinrate = it
                )
            }
        },
        onIconChangeClick = {
            settings.showIcon = it
            settings.editor.putBoolean(settings.iconKey, it).apply()
            displaySettingFlow.update { state ->
                state.copy(
                    showTypeIcon = it
                )
            }
        }, onIndicatorChangeClick = {
            settings.showIndicator = it
            settings.editor.putBoolean(settings.indicatorKey, it).apply()
            displaySettingFlow.update { state ->
                state.copy(
                    showIndicator = it
                )
            }
        },
        onSliderChange = {
            winrateFlow.update { _ ->
                it
            }
        }
    )

}