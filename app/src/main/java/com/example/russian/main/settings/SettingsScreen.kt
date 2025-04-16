package com.example.russian.main.settings

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.main.Id
import com.example.russian.main.settings.comp.GoToButton
import com.example.russian.main.settings.comp.SettingsParagraph
import com.example.russian.main.settings.comp.SwitchRow
import com.example.russian.main.settings.comp.TestCardOptionButton
import com.example.russian.main.settings.comp.WinrateSlider
import com.example.russian.main.stats.CardOfStats
import com.example.russian.main.stats.comp.stats.TaskCardUiState
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.todo.fakeSettingActions
import com.example.russian.main.wrappers.GameSettings


@Composable
fun SettingsScreen(
    settingActions: SettingActions,
    statesHolder: SettingsStatesHolder,
    openRateForm: () -> Unit,
    openTelegram: () -> Unit
) {

    val actions by remember(Unit) {
        mutableStateOf(settingActions)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SettingsParagraph("Режим тренировки") {

            val switchModifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 12.dp)

            SwitchRow(
                switchModifier,
                statesHolder.gameSettings.vibrate,
                "Вибрация",
                actions.onVibrationClick
            )

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            SwitchRow(
                switchModifier,
                statesHolder.gameSettings.playSound,
                "Звук",
                actions.onSoundClick
            )
        }

        SettingsParagraph("Статистика") {
            val cardModifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 8.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(5.dp)
                )

            CardOfStats(
                TaskCardUiState(
                    Id(0L),
                    "Отображение слова",
                    statesHolder.winrate.toDouble(),
                    hasBeenAnswered = true,
                    R.drawable.icon_no_back_dark,
                    statesHolder.statDisplaySetting,
                    isFavorite = false
                ),
                modifier = cardModifier
            ){}

            WinrateSlider(
                Modifier.padding(bottom = 8.dp), statesHolder.winrate.toFloat()
            ){
                actions.onSliderChange(it)
            }

            Row {

                val icon1 = ImageVector.vectorResource(R.drawable.ic_launcher_foreground)
                val icon2 = ImageVector.vectorResource(R.drawable.percentage_svgrepo_com)
                val icon3 =
                    ImageVector.vectorResource(R.drawable.interface_ui_loading_progress_bar_svgrepo_com)

                TestCardOptionButton(
                    Modifier
                        .padding(end = 4.dp)
                        .weight(1F)
                        .height(48.dp), icon1,
                    statesHolder.statDisplaySetting.showTypeIcon
                ) { actions.onIconChangeClick(it) }
                TestCardOptionButton(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1F)
                        .height(48.dp), icon2,
                    statesHolder.statDisplaySetting.showWinrate
                ) { actions.onWinrateChangeClick(it) }
                TestCardOptionButton(
                    Modifier
                        .padding(start = 4.dp)
                        .weight(1F)
                        .height(48.dp), icon3,
                    statesHolder.statDisplaySetting.showIndicator
                ) { actions.onIndicatorChangeClick(it) }
            }
        }

        SettingsParagraph("О нас") {

            val icon1 = ImageVector.vectorResource(R.drawable.telegram_svgrepo_com)
            val icon2 = ImageVector.vectorResource(R.drawable.rating_rate_svgrepo_com)

            Row {
                //telegram
                GoToButton(
                    Modifier
                        .padding(end = 4.dp)
                        .weight(1F)
                        .height(48.dp),
                    icon1,
                    openTelegram
                )
                //google form
                GoToButton(
                    Modifier
                        .weight(1F)
                        .height(48.dp),
                    icon2,
                    openRateForm
                )

            }
        }

    }
}







@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SettingsPreview(
    paddingValues: PaddingValues = PaddingValues()
) {
    RussianTheme {
        SettingsScreen(
            fakeSettingActions,
            SettingsStatesHolder(
                GameSettings(true, false),
                statDisplaySetting = StatDisplaySetting(false, true, true),
                0.66F
            ), {}, {}
        )
    }

}