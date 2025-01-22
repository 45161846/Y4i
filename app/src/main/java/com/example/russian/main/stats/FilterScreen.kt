package com.example.russian.main.stats

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
import com.example.russian.main.Id
import com.example.russian.main.stats.comp.filter.BoolButton
import com.example.russian.main.stats.comp.filter.FilterScreenActions
import com.example.russian.main.stats.comp.filter.FilterState
import com.example.russian.main.stats.comp.filter.ParagraphView
import com.example.russian.main.stats.comp.filter.PercentageBounds
import com.example.russian.main.stats.comp.filter.PlaylistColumn
import com.example.russian.main.stats.comp.filter.ResetButton
import com.example.russian.main.stats.comp.filter.SortBy
import com.example.russian.main.stats.comp.filter.SortDirection
import com.example.russian.main.stats.comp.filter.SortType
import com.example.russian.game.back.data.entity.playlist.Playlist
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.stats.comp.filter.MarkedPlaylist
import com.example.russian.main.stats.comp.filter.MarkedPlaylistVariant

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


            val playlists = when(state.markedPlaylists){
                is MarkedPlaylistVariant.All -> emptyList()
                is MarkedPlaylistVariant.Partial -> state.markedPlaylists.markedPlaylists
            }
            PlaylistColumn(
                playlists,
                modifier,
                actions.onPlaylistClick
            )
        }

        Spacer(
            modifier = Modifier
                .size(16.dp)
        )

        ParagraphView(stringResource(R.string.sort_by)) {
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
            onClick = actions.onShowUnansweredClick
        )

        Spacer(modifier = Modifier.weight(1F))

        Button(
            onClick = {
                actions.onSaveClick()
                closeFilter()
            }
        ) {
            Text(
                stringResource(R.string.save_filter),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {

    val state = (FilterState(
        MarkedPlaylistVariant.Partial(
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
            )
        ),
        SortType.BY_WIN_RATE(SortDirection.DOWN),
        showUnanswered = true,
        bounds = PercentageBounds(20, 87)
    )
            )

    RussianTheme {
        FilterScreen(
            state,
            testFilterActions()
        ) {}
    }
}

fun testFilterActions() = FilterScreenActions(
    {}, {}, {}, {}, {}
)