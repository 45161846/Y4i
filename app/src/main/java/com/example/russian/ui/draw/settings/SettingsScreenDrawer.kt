package com.example.russian.ui.draw.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.ui.actions.MyActions
import com.example.russian.ui.draw.common.RoundIconButton
import com.example.russian.ui.draw.stats.TestCardOfStats
import com.example.russian.ui.draw.test.testSettingActions
import com.example.russian.ui.draw.test.testSettingScreenData
import com.example.russian.ui.theme.PrimaryBackground
import com.example.russian.ui.theme.ThirdBackground
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun DrawSettingsContent(
    screenState: SettingScreenData,
    settingActions: SettingActions,
    paddingValues: PaddingValues
) {
    val backColor = PrimaryBackground

    val actions by remember(1) {
        mutableStateOf(settingActions)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backColor)
            .padding(
                bottom = paddingValues.calculateBottomPadding(),
                top = 12.dp,
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SettingsParagraph("Режим тренировки") {

            val switchModifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(ThirdBackground, RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp)

            SwitchRow(
                switchModifier,
                screenState.vibrationState,
                actions.onVibrationClick
            )

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            SwitchRow(
                switchModifier,
                screenState.soundState,
                actions.onSoundClick
            )
        }

        SettingsParagraph("Статистика") {
            val cardModifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 8.dp)
                .background(
                    ThirdBackground,
                    RoundedCornerShape(5.dp)
                )

            TestCardOfStats(screenState.testStatsCardState, cardModifier)

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
                        .weight(1F), icon1,
                    screenState.testStatsCardState.showTypeIcon.collectAsState().value
                ) { actions.onIconChangeClick(it) }
                TestCardOptionButton(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1F), icon2,
                    screenState.testStatsCardState.showWinrate.collectAsState().value
                ) { actions.onWinrateChangeClick(it) }
                TestCardOptionButton(
                    Modifier
                        .padding(start = 4.dp)
                        .weight(1F), icon3,
                    screenState.testStatsCardState.showIndicator.collectAsState().value
                ) { actions.onIndicatorChangeClick(it) }
            }
        }

        SettingsParagraph("О нас") {

            val icon1 = ImageVector.vectorResource(R.drawable.telegram_svgrepo_com)
            val icon2 = ImageVector.vectorResource(R.drawable.rating_rate_svgrepo_com)

            Row {
                //telegram
                RoundIconButton(
                    Modifier
                        .padding(end = 4.dp)
                        .weight(1F), icon1
                ) {}
                //google form
                RoundIconButton(Modifier.weight(1F), icon2) {
                    actions.onRatingClicked()
                }
            }
        }

    }
}

data class SettingScreenData(
    val vibrationState: SwitchState,
    val soundState: SwitchState,
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
@Preview
private fun Preview() {
    DrawSettingsContent(testSettingScreenData(), testSettingActions(), PaddingValues())
}