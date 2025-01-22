package com.example.russian.main.prac

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.russian.main.Id
import com.example.russian.main.prac.local.DrawPracticeContent
import com.example.russian.main.prac.local.LocalPracViewModel
import com.example.russian.main.prac.remote.PracRemoteScreen
import com.example.russian.main.prac.remote.RemotePlaylistViewModel

@Composable
fun PracScreen(
    localViewModel: LocalPracViewModel,
    remoteViewModel: RemotePlaylistViewModel,
    pagerStage: PagerState,
    navigateToRemoteDetails: () -> Unit,
    navigateToLocalDetails: () -> Unit
) {

    val localState = localViewModel.pracScreenLocalUiState.collectAsStateWithLifecycle()
    val remoteState = remoteViewModel.screenUiState.collectAsStateWithLifecycle()

    HorizontalPager(
        pagerStage
    ) { it ->
        when(it){
            0 -> DrawPracticeContent(
                state = localState.value,
                onMove = localViewModel::onMovePlaylist,
                onClick = {
                    localViewModel.onPlaylistClick(it)
                    navigateToLocalDetails()
                }
            )
            1 -> PracRemoteScreen(
                remoteState.value
            ){
                remoteViewModel.open(it)
                navigateToRemoteDetails()
            }
        }
    }
}
