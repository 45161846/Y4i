package com.example.russian.main.prac.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remotelogin.draw.Loading
import com.example.russian.main.theme.RussianTheme
import com.example.russian.main.theme.TransparentBlack
import com.example.russian.main.theme.TransparentWhite80
import com.example.russian.main.prac.remote.PlaylistColor
import com.example.russian.main.prac.remote.testRemotePlaylistState

@Composable
fun RemotePlaylistDetails(
    state: PlaylistDetailsUiState.Remote
) {
    when (state) {
        is PlaylistDetailsUiState.Remote.Loading -> Loading()
        is PlaylistDetailsUiState.Remote.Details -> {
            when (state.details) {

                is PlaylistOverView.Remote.Loading -> {
                    Loading()
                    return
                }

                is PlaylistOverView.Remote.OverView -> {
                    ScreenWithOverview(state.content, state.details)
                }
            }
        }
    }
}

@Composable
private fun ScreenWithOverview(
    content: PlaylistContent.Remote,
    overView: PlaylistOverView.Remote.OverView
) {

    val backColor = when(overView.color){
        is PlaylistColor.Regular -> MaterialTheme.colorScheme.surface
        is PlaylistColor.Special -> overView.color.color
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backColor)
        , horizontalAlignment = Alignment.Start,
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
                .padding(8.dp)
                ,
            text = overView.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.onSecondary.copy(
                    alpha = 0.87F
                )
            ),
        )

        Text(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.secondary,
//                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(horizontal = 8.dp),
            text = overView.description,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSecondary.copy(
                    alpha = 0.6F
                ),
            )
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun PreviewOverView() {

    val playlist = testRemotePlaylistState().playlists[1]

    val overView = PlaylistOverView.Remote.OverView(
        title = playlist.title,
        description = playlist.description,
        color = PlaylistColor.Regular,
        previewTasks = playlist.previewTasks,
        rating = playlist.rating,
        capacity = playlist.capacity
    )

    RussianTheme {
        ScreenWithOverview(
            PlaylistContent.Remote.Loading, overView
        )
    }
}