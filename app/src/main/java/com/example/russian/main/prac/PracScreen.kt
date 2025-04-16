package com.example.russian.main.prac

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.main.prac.local.DrawPracticeContent
import com.example.russian.main.prac.local.LocalPracViewModel
import com.example.russian.main.prac.remote.PracRemoteScreen
import com.example.russian.main.prac.remote.RemotePlaylistViewModel
import com.example.russian.main.stats.comp.stats.BottomBarState
import kotlinx.coroutines.flow.combine

@Composable
fun PracScreen(
    localViewModel: LocalPracViewModel,
    remoteViewModel: RemotePlaylistViewModel,
    pagerStage: PagerState,
    navigateToRemoteDetails: () -> Unit,
    navigateToLocalDetails: () -> Unit,
    changeBottomBarState: (BottomBarState) -> Unit
) {

    val localState = localViewModel.pracScreenLocalUiState.collectAsStateWithLifecycle()
    val remoteState = remoteViewModel.screenUiState.collectAsStateWithLifecycle()

    var remoteBottomState: BottomBarState by remember {
        mutableStateOf(BottomBarState.Show)
    }

    LaunchedEffect(pagerStage) {
        combine(
            snapshotFlow { pagerStage.currentPage },
            snapshotFlow { remoteBottomState }) { page, state ->
            if (page == 0) {
                BottomBarState.Show
            } else {
                state
            }
        }.collect {
            changeBottomBarState(it)
        }
    }

    HorizontalPager(
        pagerStage
    ) { it ->
        when (it) {
            0 -> {

                DrawPracticeContent(
                    state = localState.value,
                    onMove = localViewModel::onMovePlaylist,
                    onClick = {
                        localViewModel.onPlaylistClick(it)
                        navigateToLocalDetails()
                    },
                )
            }

            1 -> PracRemoteScreen(
                state = remoteState.value,
                loadPlaylists = remoteViewModel::appendPlaylists,
                changeBottomBarState = {
                    remoteBottomState = it
                },
                navigateToDetails = {
                    remoteViewModel.open(it)
                    navigateToRemoteDetails()
                }
            )
        }
    }
}
