package com.example.russian.main.prac.remote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.main.Id
import com.example.russian.main.custom.FullScreenColumn
import com.example.russian.main.prac.PracScreenStage
import com.example.russian.main.prac.RemotePlaylistUi
import com.example.russian.main.stats.comp.stats.BottomBarState
import com.example.russian.main.theme.RussianTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun PracRemoteScreen(
    state: PracScreenStage.Remote,
    navigateToDetails: (Id) -> Unit,
    loadPlaylists: (Int) -> Unit,
    changeBottomBarState: (BottomBarState) -> Unit
) {
    val shimmer = Modifier
        .shimmer()

    val listState = rememberLazyListState()


    val needLoad by remember(state.lastLoadedIndex) {
        derivedStateOf {
            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?: 0) >= state.lastLoadedIndex
        }
    }

    val showBottomBar by remember { derivedStateOf {
        listState.firstVisibleItemIndex < 2 || listState.lastScrolledBackward
    } }

    changeBottomBarState(BottomBarState.valueOf(showBottomBar))

    if (needLoad) {
        loadPlaylists(state.lastLoadedIndex)
    }

    FullScreenColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(state.playlists.size, key = { it }) {
            when (val current = state.playlists[it]) {
                is RemotePlaylistUi.Loading -> RemotePlaylistShimmer(
                    modifier = shimmer
                )

                is RemotePlaylistUi.Data -> {
                    RemotePlaylistCard(current.playlist) {
                        navigateToDetails(current.playlist.remoteId)
                    }
                }

                is RemotePlaylistUi.EndCard.Error -> {
                    ErrorEnding(current.message)
                }

                is RemotePlaylistUi.EndCard.NothingMore -> {
                    NothingMore()
                }
            }
        }
    }
}

@Composable
private fun Loading() {

    val shimmer = Modifier
        .shimmer()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn {
            items(10, { it }) {
                RemotePlaylistShimmer(modifier = shimmer)
            }
        }
    }
}


@Preview
@Composable
private fun RemotePreview() {
    RussianTheme {
        RemotePlaylistCard(
            (testRemotePlaylistState().playlists[1] as RemotePlaylistUi.Data).playlist
        ) {

        }
    }
}

@Preview
@Composable
private fun RemotePreviewLoading() {
    RussianTheme {
        Loading()
    }
}