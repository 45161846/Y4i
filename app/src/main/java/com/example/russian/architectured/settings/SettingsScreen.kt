package com.example.russian.architectured.settings

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.architectured.settings.comp.CustomSwitch
import com.example.russian.architectured.settings.comp.GoToButton
import com.example.russian.architectured.settings.comp.SettingsParagraph
import com.example.russian.architectured.stats.TestCardOfStats
import com.example.russian.architectured.todo.fakeSettingActions
import com.example.russian.main.ui.actions.MyActions
import com.example.russian.main.ui.draw.settings.TestCardOptionButton
import com.example.russian.main.ui.draw.settings.TestStatsCardState
import com.example.russian.main.ui.draw.settings.WinrateSlider
import com.example.russian.main.ui.draw.stats.comp.MyFilterOptionText
import com.example.russian.main.ui.draw.test.testStatsCardState
import com.example.russian.main.ui.theme.RussianTheme
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun SettingsScreen(
    settingActions: SettingActions,
    statesHolder: SettingsStatesHolder
) {

    val actions by remember(1) {
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
                statesHolder.vibrationState,
                actions.onVibrationClick
            )

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            SwitchRow(
                switchModifier,
                statesHolder.soundState,
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

            TestCardOfStats(statesHolder.testStatsCardState, cardModifier)

            WinrateSlider(
                Modifier.padding(bottom = 8.dp), actions.onSliderChange
            )

            Row {

                val icon1 = ImageVector.vectorResource(R.drawable.ic_launcher_foreground)
                val icon2 = ImageVector.vectorResource(R.drawable.percentage_svgrepo_com)
                val icon3 =
                    ImageVector.vectorResource(R.drawable.interface_ui_loading_progress_bar_svgrepo_com)

                TestCardOptionButton(
                    Modifier
                        .padding(end = 4.dp)
                        .weight(1F)
                        .height(48.dp)
                    , icon1,
                    statesHolder.testStatsCardState.showTypeIcon.collectAsState().value
                ) { actions.onIconChangeClick(it) }
                TestCardOptionButton(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1F)
                        .height(48.dp)
                    , icon2,
                    statesHolder.testStatsCardState.showWinrate.collectAsState().value
                ) { actions.onWinrateChangeClick(it) }
                TestCardOptionButton(
                    Modifier
                        .padding(start = 4.dp)
                        .weight(1F)
                        .height(48.dp)
                    , icon3,
                    statesHolder.testStatsCardState.showIndicator.collectAsState().value
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
                        .height(48.dp)
                    , icon1
                ) {}
                //google form
                GoToButton(
                    Modifier
                        .weight(1F)
                        .height(48.dp)
                    ,
                    icon2) {
                    actions.onRatingClicked()
                }
            }
        }

    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SwitchRow(modifier: Modifier, statesHolder: SwitchState, onCheckChange: (Boolean) -> Unit) {
    Row(
        modifier = modifier
            .padding(vertical = 12.dp, horizontal = 8.dp)

        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val checked = statesHolder.checked.collectAsState().value

        Text(
            statesHolder.text,
            style = MaterialTheme.typography.bodyMedium
        )

        CustomSwitch(
            checked,
            32.dp,
            onCheckChange = onCheckChange
        )

    }
}

data class SettingsStatesHolder(
    val vibrationState: SwitchState = SwitchState("Вибрация", MutableStateFlow(true)),
    val soundState: SwitchState = SwitchState("Звук"),
    val testStatsCardState: TestStatsCardState,
)


data class SwitchState(
    val text: String = "",
    val checked: MutableStateFlow<Boolean> = MutableStateFlow(false)
)

data class SettingActions(
    val onSoundClick: (Boolean) -> Unit,
    val onVibrationClick: (Boolean) -> Unit,
    val onSliderChange: (Float) -> Unit,
    val onIconChangeClick: (Boolean) -> Unit,
    val onWinrateChangeClick: (Boolean) -> Unit,
    val onIndicatorChangeClick: (Boolean) -> Unit,
    val onTelegramClick: () -> Unit,
    val onRatingClicked: () -> Unit
) : MyActions()


@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SettingsPreview(
    paddingValues: PaddingValues = PaddingValues()
) {
    RussianTheme {
        SettingsScreen(
            fakeSettingActions,
            SettingsStatesHolder(
                testStatsCardState = testStatsCardState()
            )
        )
    }

}