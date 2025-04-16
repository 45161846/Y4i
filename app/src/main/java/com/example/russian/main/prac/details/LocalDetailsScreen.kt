package com.example.russian.main.prac.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remotelogin.draw.Loading
import com.example.russian.main.Id
import com.example.russian.main.prac.RemotePlaylistUi
import com.example.russian.main.prac.details.bottom.BottomFilterActions
import com.example.russian.main.prac.details.bottom.BottomFilterState
import com.example.russian.main.prac.details.bottom.PlaylistDetailsBottomFilter
import com.example.russian.main.prac.remote.testRemotePlaylistState
import com.example.russian.main.settings.StatDisplaySetting
import com.example.russian.main.stats.comp.stats.DrawToTopButton
import com.example.russian.main.stats.comp.stats.SearchFilterRow
import com.example.russian.main.theme.RussianTheme
import kotlinx.coroutines.launch

@Composable
fun LocalDetailsScreen(
    state: PlaylistDetailsUiState.Local,
    filterState: BottomFilterState,
    filterActions: BottomFilterActions,
    onSearch: (String) -> Unit,
    onStartGame: (Id) -> Unit
) {
    when (state) {
        is PlaylistDetailsUiState.Local.Loading -> Loading()
        is PlaylistDetailsUiState.Local.Details -> {
            when (state.details) {

                is PlaylistOverView.Local.Loading -> {
                    Loading()
                    return
                }

                is PlaylistOverView.Local.OverView -> {
                    ScreenWithOverview(
                        state.content, state.details,
                        filterState, filterActions,
                        onSearch
                    ) {
                        onStartGame(state.details.id)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenWithOverview(
    content: PlaylistContent.Local,
    overView: PlaylistOverView.Local.OverView,
    filterState: BottomFilterState,
    filterActions: BottomFilterActions,
    onSearch: (String) -> Unit,
    onStartGame: () -> Unit
) {

    val backColor = MaterialTheme.colorScheme.surface
    val scope = rememberCoroutineScope()

    var showFilter by remember {
        mutableStateOf(false)
    }

    val listState = rememberLazyListState()

    var contentIsShown by remember {
        mutableStateOf(
            content is PlaylistContent.Local.Content
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,

        ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondary,
//                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(8.dp),
            text = overView.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onSecondary.copy(
                    alpha = 0.87F
                )
            ),
        )

        when (content) {
            is PlaylistContent.Local.Loading -> {
                contentIsShown = false
                SearchFilterShimmer()
            }

            is PlaylistContent.Local.Content -> {
                contentIsShown = true
                SearchFilterRow(
                    onSearch = onSearch, modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondary),
                    onFilterClick = {
                        showFilter = true
                    }
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondary
                )
                .padding(horizontal = 8.dp),
            text = overView.description,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSecondary.copy(
                    alpha = 0.6F
                ),
            )
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val contentModifier = Modifier.fillMaxSize()

            LocalDetailsContent(
                content,
                listState,
                contentModifier
            )

            val visible by remember {
                derivedStateOf {
                    listState.firstVisibleItemIndex == 0
                            && contentIsShown
                }
            }

            StartGameButton(
                visible,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                onStartGame
            )

            DrawToTopButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .windowInsetsPadding(WindowInsets.navigationBars),
                listState = listState,
                isBottomBarVisible = visible,
                onClick = {
                    scope.launch {
                        listState.scrollToItem(0)
                    }
                }
            )
        }


        val sheetState = rememberStandardBottomSheetState(
            skipHiddenState = false
        )

        if ((showFilter)) {
            PlaylistDetailsBottomFilter(
                sheetState,
                filterState,
                filterActions,
                onDismiss = {
                    showFilter = false
                }
            )
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun PreviewOverView() {

    val playlist = (testRemotePlaylistState().playlists[1] as RemotePlaylistUi.Data).playlist

    val overView = PlaylistOverView.Local.OverView(
        id = Id(0),
        title = playlist.title,
        description = playlist.description,
        capacity = playlist.capacity
    )

    RussianTheme {
        ScreenWithOverview(
            PlaylistContent.Local.Loading(
                StatDisplaySetting.Empty()
            ),
            overView,
            BottomFilterState.default(),
            BottomFilterActions.test(),
            {},
            {}
        )
    }
}