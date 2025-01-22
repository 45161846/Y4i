package com.example.russian.main.prac.remote

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.Id
import com.example.russian.main.prac.PracScreenStage

@Composable
fun PracRemoteScreen(
    state: PracScreenStage.Remote,
    navigateToDetails: (Id) -> Unit
) {
    when (state) {
        is PracScreenStage.Remote.Loading -> Loading()
        is PracScreenStage.Remote.Data -> Content(state, navigateToDetails)
    }
}

@Composable
private fun Content(
    state: PracScreenStage.Remote.Data,
    navigateToDetails: (Id) -> Unit
){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.playlists.size, key = { it }) {
            val current = state.playlists[it]
            RemotePlaylistCard(current){
                navigateToDetails(current.remoteId)
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        //TODO
        //change to shimmer card
        Text("Loading")
    }
}

@Preview
@Composable
private fun RemotePreview() {
    RussianTheme {
        PracRemoteScreen(testRemotePlaylistState()){}
    }
}