package com.example.russian.ui.draw.test

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.russian.R
import com.example.russian.back.data.entity.MyTask
import com.example.russian.back.data.entity.Statistics
import com.example.russian.back.data.entity.WordStatistics
import com.example.russian.enums.SortTypeMode
import com.example.russian.ui.draw.stats.comp.PlaylistState
import com.example.russian.ui.draw.common.SimpleBooleanState
import com.example.russian.ui.draw.settings.SettingActions
import com.example.russian.ui.draw.settings.SettingScreenData
import com.example.russian.ui.draw.settings.SwitchState
import com.example.russian.ui.draw.settings.TestStatsCardState
import com.example.russian.ui.draw.stats.comp.PlaylistViewStateParent
import com.example.russian.ui.draw.stats.comp.SortFilterState
import com.example.russian.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.ui.draw.stats.screen.FilterScreenActions
import com.example.russian.ui.state.ClickableWord
import com.example.russian.ui.state.StatCardUIState
import com.example.russian.ui.theme.ThirdBackground
import kotlinx.coroutines.flow.MutableStateFlow

fun testDataWordsList(): List<WordStatistics> {

    val words = listOf(
        MyTask(1L, "word 1", 2),
        MyTask(2L, "word 2", 1),
        MyTask(3L, "word 3", 0),
        MyTask(4L, "word 4", 1),
        MyTask(5L, "word 5", 1),
        MyTask(6L, "word 6", 0),
        MyTask(7L, "word 7", 2),
        MyTask(8L, "word 8", 0),
        MyTask(9L, "word 9", 2),
        MyTask(10L, "word 10", 0),
        MyTask(11L, "word 11", 1),
        MyTask(12L, "word 12", 0),
        MyTask(13L, "word 13", 2),
        MyTask(14L, "word 14", 0),
        MyTask(15L, "word 15", 1),
        MyTask(16L, "word 16", 0),
        MyTask(15L, "word 15", 1),
        MyTask(16L, "word 16", 0),
    )

    val stats = listOf(
        Statistics(1L, 1L, 15, 7, "word 1"),
        Statistics(2L, 2L, 3, 3, "word 2"),
        Statistics(3L, 3L, 0, 0, "word 3"),
        Statistics(4L, 4L, 1, 0, "word 4"),
        Statistics(5L, 5L, 8, 7, "word 5"),
        Statistics(6L, 6L, 100, 7, "word 6"),
        Statistics(7L, 7L, 100, 0, "word 7"),
        Statistics(8L, 8L, 13, 7, "word 8"),
        Statistics(9L, 9L, 15, 7, "word 9"),
        Statistics(10L, 10L, 15, 7, "word 10"),
        Statistics(11L, 11L, 15, 3, "word 11"),
        Statistics(12L, 12L, 22, 7, "word 12"),
        Statistics(13L, 13L, 3, 3, "word 9"),
        Statistics(14L, 14L, 15, 7, "word 10"),
        Statistics(15L, 15L, 120, 7, "word 11"),
        Statistics(16L, 16L, 15, 2, "word 12"),
        Statistics(15L, 15L, 120, 7, "word 11"),
        Statistics(16L, 16L, 15, 2, "word 12"),
    )

    return List(words.size) {
        WordStatistics(words[it], stats[it])
    }
}


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