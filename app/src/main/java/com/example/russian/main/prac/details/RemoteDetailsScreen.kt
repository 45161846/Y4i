package com.example.russian.main.prac.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.remotelogin.draw.Loading
import com.example.russian.main.Id
import com.example.russian.main.custom.FullScreenColumn
import com.example.russian.main.data.remote.PreviewTask
import com.example.russian.main.data.remote.RemotePlaylist
import com.example.russian.main.prac.RemotePlaylistUi
import com.example.russian.main.prac.remote.PlaylistColor
import com.example.russian.main.prac.remote.testRemotePlaylistState
import com.example.russian.main.theme.RussianTheme

@Composable
fun RemotePlaylistDetails(
    state: PlaylistDetailsUiState.Remote,
    onSave: (
        PlaylistOverView.Remote.OverView, PlaylistContent.Remote
    ) -> Unit,
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
                    ScreenWithOverview(
                        state.content,
                        state.details,
                        onSave = {
                            onSave(state.details, state.content)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenWithOverview(
    content: PlaylistContent.Remote,
    overView: PlaylistOverView.Remote.OverView,
    onSave: () -> Unit
) {

    val topBarColor = when (overView.color) {
        is PlaylistColor.Regular -> MaterialTheme.colorScheme.secondary
        is PlaylistColor.Special -> overView.color.topBarColor
    }
    val textColor = when (overView.color) {
        is PlaylistColor.Regular -> MaterialTheme.colorScheme.onSecondary
        is PlaylistColor.Special -> overView.color.textColor
    }

    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

        ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    topBarColor,
//                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(8.dp),
            text = overView.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = textColor.copy(
                    alpha = 0.87F
                )
            ),
        )

        Text(
            modifier = Modifier
                .background(
                    topBarColor,
//                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
                .padding(horizontal = 8.dp),
            text = overView.description,
            style = MaterialTheme.typography.labelLarge.copy(
                color = textColor.copy(
                    alpha = 0.6F
                ),
            )
        )

        when (content) {
            is PlaylistContent.Remote.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Loading()
                }
            }

            is PlaylistContent.Remote.Content -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Content(
                        content.cards.map {
                            it.previewText
                        },
                        listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 4.dp)
                    )

                    Button(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.safeGestures),
                        onClick = onSave
                    ) {
                        Text(
                            "Сохранить на устройство"
                        )
                    }

                }

            }
        }
    }
}

typealias Task = String

@Composable
fun Content(
    tasks: List<Task>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {

    FullScreenColumn(
        modifier = modifier,
        state = listState,
    ) {
        items(tasks.size, { it }) {
            RemoteTaskCard(
                tasks[it],
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)

            )
        }
        item {
            Spacer(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
            )
        }
    }


}

@Composable
fun RemoteTaskCard(
    text: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(30)
    ) {
        Text(
            text,
            modifier = Modifier
                .padding(2.dp)
        )
    }

}

@Preview(
    showBackground = true
)
@Composable
private fun PreviewOverView() {

    val playlist = (testRemotePlaylistState().playlists[1] as RemotePlaylistUi.Data).playlist

    val overView = PlaylistOverView.Remote.OverView(
        id = playlist.remoteId,
        title = playlist.title,
        description = playlist.description,
        color = PlaylistColor.Regular,
        previewTasks = playlist.previewTasks,
        rating = playlist.rating,
        capacity = playlist.capacity
    )

    RussianTheme {
        ScreenWithOverview(
            PlaylistContent.Remote.Content(
                List(50) {
                    PreviewTask(
                        Id(it.toLong()),
                        "Task $it"
                    )
                }
            ), overView
        ) {}
    }
}