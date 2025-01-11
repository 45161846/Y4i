package com.example.russian.main.ui.draw.stats.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.R
import com.example.russian.main.ui.draw.test.testPlaylistState
import com.example.russian.main.ui.modifier.clickableWithoutRipple

@Composable
fun PlaylistView(
    state: PlaylistViewStateParent,
    modifier: Modifier,
    onPlaylistClick: (Int) -> Unit
) {
    Column(modifier) {
        state.playlistStates.forEachIndexed {index, state ->
            Card(state){
                onPlaylistClick(index)
            }
        }
    }
}

@Composable
private fun Card(playlist: PlaylistState, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickableWithoutRipple(onClick)
    ) {

        MyFilterOptionText(text = playlist.title)

        Spacer(modifier = Modifier.weight(1F))

        val image = if(playlist.checked) R.drawable.check_square else R.drawable.uncheck_square

        Image(ImageVector.vectorResource(image), null)
    }
}

data class PlaylistState(
    val title: String,
    var checked: Boolean
)

sealed class PlaylistViewStateParent(
    open val playlistStates: List<PlaylistState>
){
    data class PlaylistViewState(
        override var playlistStates: List<PlaylistState>
    ): PlaylistViewStateParent(playlistStates)

    data object Loading: PlaylistViewStateParent(emptyList())
}





@Preview
@Composable
private fun PlaylistPreview() {

    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 12.dp)

    PlaylistView(testPlaylistState(), modifier, {})
}