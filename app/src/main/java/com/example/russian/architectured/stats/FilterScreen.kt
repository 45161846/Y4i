package com.example.russian.architectured.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGesturesPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.architectured.Id
import com.example.russian.architectured.stats.comp.filter.BoolButton
import com.example.russian.architectured.stats.comp.filter.FilterScreenActions
import com.example.russian.architectured.stats.comp.filter.FilterState
import com.example.russian.architectured.stats.comp.filter.ParagraphView
import com.example.russian.architectured.stats.comp.filter.PercentageBounds
import com.example.russian.architectured.stats.comp.filter.PlaylistColumn
import com.example.russian.architectured.stats.comp.filter.ResetButton
import com.example.russian.architectured.stats.comp.filter.SortBy
import com.example.russian.architectured.stats.comp.filter.SortDirection
import com.example.russian.architectured.stats.comp.filter.SortType
import com.example.russian.main.back.data.entity.playlist.Playlist
import com.example.russian.main.ui.state.MarkedPlaylist
import com.example.russian.main.ui.theme.RussianTheme

@Composable
fun FilterScreen(
    state: FilterState,
    actions: FilterScreenActions,
    closeFilter: () -> Unit
) {


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
//            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
            .safeGesturesPadding()
    ) {

        ResetButton(
            stringResource(id = R.string.filter_screen_reset_text),
        ) {
            actions.onResetClick()
        }

        ParagraphView(stringResource(id = R.string.filter_screen_title1)) {
            val modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 12.dp)

            PlaylistColumn(
                state.markedPlaylists,
                modifier,
                actions.onPlaylistClick
            )
        }

        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        ParagraphView("Сортировать по") {
            val modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

            SortBy(
                state.sortType,
                modifier,
                actions.onSortTypeClick
            )
        }

        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        BoolButton(
            state.showUnanswered,
            onClick =  actions.onShowUnansweredClick
        )

        Spacer(modifier = Modifier.weight(1F))

        Button(
            onClick = {
                actions.onSaveClick()
                closeFilter()
            }
        ){
            Text("Save", style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {

    val state = (FilterState(
        listOf(
            MarkedPlaylist(
                Playlist(Id(1L), "Наречия", 0L),
                true
            ),
            MarkedPlaylist(
                Playlist(Id(1L), "Паронимы", 0L),
                true
            ),
            MarkedPlaylist(
                Playlist(Id(1L), "Н/НН с частями речи", 0L),
                false
            )
        ),
        SortType.BY_WIN_RATE(SortDirection.DOWN),
        showUnanswered = true,
        bounds = PercentageBounds(20, 87)
    ))

    RussianTheme {
        FilterScreen(
            state,
            testFilterActions()
        ){}
    }
}

fun testFilterActions() = FilterScreenActions(
    {},{},{},{},{}
)