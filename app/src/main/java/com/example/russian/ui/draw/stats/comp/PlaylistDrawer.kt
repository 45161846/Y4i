package com.example.russian.ui.draw.stats.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.russian.ui.draw.test.testPlaylistState
import com.example.russian.ui.theme.FiltersScreenButtonActive

@Composable
fun PlaylistView(
    state: PlaylistViewState,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Card(playlist: PlaylistState, onClick: () -> Unit) {
    val click = {
        onClick()
        playlist.checked = playlist.checked.not()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable{
                onClick()
            }
    ) {

        MyFilterOptionText(text = playlist.title)

        Spacer(modifier = Modifier.weight(1F))
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            Checkbox(
                modifier = Modifier.scale(1.2F),
                checked = playlist.checked,
                onCheckedChange = {
                    click()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = FiltersScreenButtonActive
                )
            )
        }

    }
}

data class PlaylistState(
    val title: String,
    var checked: Boolean
)

data class PlaylistViewState(
    val playlistStates: List<PlaylistState>
)

@Preview
@Composable
private fun PlaylistPreview() {

    val modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 12.dp)

    PlaylistView(testPlaylistState(), modifier, {})
}