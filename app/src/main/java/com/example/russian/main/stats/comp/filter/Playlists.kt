package com.example.russian.main.stats.comp.filter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.russian.R

@Composable
fun PlaylistColumn(
    playlists: List<MarkedPlaylist>,
    modifier: Modifier,
    onPlaylistClick: (MarkedPlaylist) -> Unit
) {
    Column(modifier) {
        playlists.forEach{playlist ->
            Card(playlist, onPlaylistClick)
        }
    }
}

@Composable
private fun Card(markedPlaylist: MarkedPlaylist, onClick: (MarkedPlaylist) -> Unit) {

    var marked by remember(markedPlaylist.marked){
        mutableStateOf(markedPlaylist.marked)
    }
    val text = remember {
        markedPlaylist.playlist.title
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                if(marked) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(30)
            )
            .padding(8.dp)
            .clickable(null, null){
                marked = marked.not()
                onClick(markedPlaylist)
            }
    ) {

        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color =
                if(marked) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )

        Spacer(modifier = Modifier.weight(1F))

        Image(
            if(marked) painterResource(R.drawable.check_square)
            else painterResource(R.drawable.uncheck_square),
            null
        )
    }
}