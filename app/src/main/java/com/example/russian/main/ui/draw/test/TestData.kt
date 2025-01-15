package com.example.russian.main.ui.draw.test

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Modifier
import com.example.russian.main.enums.SortTypeMode
import com.example.russian.main.ui.draw.common.SimpleBooleanState
import com.example.russian.main.ui.draw.settings.SettingActions
import com.example.russian.main.ui.draw.settings.SettingScreenData
import com.example.russian.main.ui.draw.settings.SwitchState
import com.example.russian.main.ui.draw.settings.TestStatsCardState
import com.example.russian.main.ui.draw.stats.comp.PlaylistState
import com.example.russian.main.ui.draw.stats.comp.PlaylistViewStateParent
import com.example.russian.main.ui.draw.stats.comp.SortFilterState
import com.example.russian.main.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.main.ui.draw.stats.screen.FilterScreenActions
import com.example.russian.main.ui.state.ClickableWord
import kotlinx.coroutines.flow.MutableStateFlow


fun testPlaylistState() = PlaylistViewStateParent.PlaylistViewState(
    listOf(
        PlaylistState("title 1", true),
        PlaylistState("title 2", true),
        PlaylistState("title 3", false)
    )
)

fun testShowUnansweredState() = SimpleBooleanState(
    true, "Показывать неотвеченные слова",
    Modifier
        .fillMaxWidth()
        .wrapContentHeight()
)

fun testSortState() = SortFilterViewState(
    listOf(
        SortFilterState("sort type 1", SortTypeMode.DIRECT, false),
        SortFilterState("sort type 2", SortTypeMode.UNSPECIFIED, false),
        SortFilterState("sort type last", SortTypeMode.UNSPECIFIED, true)
    )
)

fun testActions() = FilterScreenActions(
    {}, {}, {}, {}, {}
)

fun testSettingScreenData() = SettingScreenData(
    vibrationState = SwitchState("Вибрация"),
    soundState = SwitchState("Звук"),
    testStatsCardState = TestStatsCardState(
        winrate = MutableStateFlow(0.5),
        showTypeIcon = MutableStateFlow(true),
        showWinrate = MutableStateFlow(true),
        showIndicator = MutableStateFlow(true)
    )
)

fun testSettingActions() = SettingActions({}, {}, {}, {}, {}, {}, {}, {})

fun testStatsCardState() = TestStatsCardState(
    winrate = MutableStateFlow(1.0),
    showIndicator = MutableStateFlow(true),
    showWinrate = MutableStateFlow(true),
    showTypeIcon = MutableStateFlow(false)
)

private lateinit var onWordClick: (Int) -> Unit

fun testClickableWord(): List<ClickableWord> {

    var counter = 0

    val states = listOf<ClickableWord>(
        (ClickableWord.Clickable(MutableStateFlow("Word 1"),true)),
        (ClickableWord.Clickable(MutableStateFlow("Word 2"), true)),
        (ClickableWord.NoClick("Word 3"))
    )

    onWordClick = {
        when(val word = states[it]){
            is ClickableWord.Clickable -> word.textFlow.value += "abx "
            is ClickableWord.NoClick -> {}
        }
    }

    return states
}

fun testWordClickFun(): (Int) -> Unit {
    return onWordClick
}