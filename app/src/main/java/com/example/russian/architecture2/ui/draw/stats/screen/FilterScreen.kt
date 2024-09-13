package com.example.russian.architecture2.ui.draw.stats.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.architecture2.ui.actions.MyActions
import com.example.russian.architecture2.ui.draw.stats.comp.ActionButton
import com.example.russian.architecture2.ui.draw.stats.comp.ParagraphView
import com.example.russian.architecture2.ui.draw.stats.comp.PlaylistView
import com.example.russian.architecture2.ui.draw.stats.comp.PlaylistViewState
import com.example.russian.architecture2.ui.draw.stats.comp.ShowUnansweredView
import com.example.russian.architecture2.ui.draw.stats.comp.SortFilterView
import com.example.russian.architecture2.ui.draw.stats.comp.SortFilterViewState
import com.example.russian.architecture2.ui.draw.stats.comp.UnansweredViewState
import com.example.russian.architecture2.ui.draw.test.testActions
import com.example.russian.architecture2.ui.draw.test.testPlaylistState
import com.example.russian.architecture2.ui.draw.test.testShowUnansweredState
import com.example.russian.architecture2.ui.draw.test.testSortState
import com.example.russian.architecture2.ui.route.StatsScreenState
import com.example.russian.architecture2.ui.state.FilterScreenData
import com.example.russian.ui.theme.PrimaryBackground
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FilterScreen(data: FilterScreenData, actions: FilterScreenActions) {
    val backgroundColor = PrimaryBackground

    val playlistState = data.playlistState.collectAsState()
    val sortState = data.sortState.collectAsState()
    val answerState = data.answerState.collectAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        val resetButton = colorResource(R.color.reset_button)
        ActionButton(
            stringResource(id = R.string.filter_screen_reset_text),
            resetButton,
            actions.onResetClick
        )

        ParagraphView(stringResource(id = R.string.filter_screen_title1)) {
            val modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 12.dp)

            PlaylistView(playlistState.value, modifier, actions.onPlaylistClick)
        }

        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        ParagraphView("Сортировать по") {
            val modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 12.dp)

            SortFilterView(sortState.value, modifier, actions.onSortTypeClick)
        }

        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        ShowUnansweredView(answerState.value, actions.onShowUnansweredClick)

        Spacer(modifier = Modifier.weight(1F))

        val saveColor = colorResource(R.color.save_button)
        ActionButton(
            stringResource(id = R.string.filter_screen_save_button_text),
            saveColor,
            actions.onSaveClick
        )
    }
}


data class FilterScreenState(
    val playlistViewState: PlaylistViewState,
    val sortFilterViewState: SortFilterViewState,
    val showUnansweredViewState: UnansweredViewState
): StatsScreenState()

class FilterScreenStateFlow(
    val playlistViewState: StateFlow<PlaylistViewState>,
    val sortFilterViewState: StateFlow<SortFilterViewState>,
    val showUnansweredViewState: StateFlow<UnansweredViewState>
)

data class FilterScreenActions(
    val onResetClick: () -> Unit,
    val onPlaylistClick: (Int) -> Unit,
    val onSortTypeClick: (Int) -> Unit,
    val onShowUnansweredClick: () -> Unit,
    val onSaveClick: () -> Unit
): MyActions()

@Preview
@Composable
private fun Preview() {
    val data = FilterScreenData(
        MutableStateFlow(testPlaylistState()),
        MutableStateFlow(testSortState()),
        MutableStateFlow(testShowUnansweredState())
    )

    FilterScreen(data, testActions())
}